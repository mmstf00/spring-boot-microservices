package com.project.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // Distributes the traffic among different instances of the same application.
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
