package com.example.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean   // @Bean annotation creates a bean of type "webClient" here
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
