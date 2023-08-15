package com.project.orderservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Manual configuration required for Observability
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class ManualConfiguration {

    private final KafkaTemplate kafkaTemplate;

    @PostConstruct
    void setup() {
        this.kafkaTemplate.setObservationEnabled(true);
    }

}
