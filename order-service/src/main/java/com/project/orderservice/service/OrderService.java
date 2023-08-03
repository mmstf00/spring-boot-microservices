package com.project.orderservice.service;

import com.project.orderservice.dto.InventoryResponse;
import com.project.orderservice.dto.OrderLineItemsDto;
import com.project.orderservice.dto.OrderRequest;
import com.project.orderservice.entity.Order;
import com.project.orderservice.entity.OrderLineItems;
import com.project.orderservice.exception.ProductNotInStockException;
import com.project.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Collect all skuCodes
        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        if (isInStock(skuCodes)) {
            orderRepository.save(order);
            return "Successfully placed order!";
        } else {
            throw new ProductNotInStockException("Product is not in stock, try again later");
        }
    }

    private boolean isInStock(List<String> skuCodes) {
        return Arrays.stream(getInventoryResponse(skuCodes))
                .allMatch(InventoryResponse::isInStock);
    }

    /**
     * Calls the Inventory Service by passing the RequestParam as list of skuCodes.
     * Instead of making multiple HTTP requests to Inventory Service for each skuCode,
     * we are making one request with list of skuCodes.
     * <p>
     * By default, WebClient will make Async request.
     * To make Synchronous request, we should add {@code block()} method.
     *
     * @param skuCodes list of skuCodes to be checked.
     * @return array of InventoryResponse containing the availability data.
     */
    private InventoryResponse[] getInventoryResponse(List<String> skuCodes) {
        return webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) // Type of the received response.
                .block();
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
