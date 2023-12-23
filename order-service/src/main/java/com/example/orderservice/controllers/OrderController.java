package com.example.orderservice.controllers;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory", fallbackMethod = "timeoutHandler")
//    @Retry(name = "inventory", fallbackMethod = "retryHandler")
    // TimeLimiter uses CompletableFuture<T> for asynchronous communications
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest request) {
        return CompletableFuture.completedFuture(orderService.placeOrder(request));
    }

    // If inventory-service is not available when order-service requests,
    // CircuitBreaker uses the following method as a fallback consuming thrown exception.
    // Request and response parameters of the method signature must be the same as in placeOrder() method.
    public CompletableFuture<String> fallbackMethod(OrderRequest request, RuntimeException exception) {
        return CompletableFuture.completedFuture("Oops, something went wrong! Please, try ordering later.");
    }

    public CompletableFuture<String> timeoutHandler(OrderRequest request, RuntimeException exception) {
        return CompletableFuture.completedFuture("Request timed out! Please, try ordering later.");
    }

    public CompletableFuture<String> retryHandler(OrderRequest request, RuntimeException exception) {
        return CompletableFuture.completedFuture("Retrying to connect!");
    }
}
