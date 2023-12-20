package com.example.orderservice.services.impls;

import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderLineItemsRequest;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderLineItem;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public String placeOrder(OrderRequest request) {
        List<String> skuCodes = new LinkedList<>();

        List<OrderLineItem> orderLineItems = request.getOrderLineItemsRequests().stream()
            .map(orderLineItemsRequest -> {
                skuCodes.add(orderLineItemsRequest.getSkuCode());
                return mapDtoToEntity(orderLineItemsRequest);
            }).toList();

        // Makes call to inventory service, and places order if product is in stock
        InventoryResponse[] response = webClientBuilder.build().get()
//            .uri("http://localhost:8082/api/v1/inventory", Map.of("skuCode", skuCodes))
            .uri("http://inventory-service/api/v1/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class)
            .block();

        assert response != null;
        boolean allProductsPresentInStock = Arrays.stream(response).allMatch(InventoryResponse::getIsInStock);

        if (!allProductsPresentInStock)
            throw new IllegalArgumentException("Product is not in stock, please, try again later");

        orderRepository.save(
            Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderLineItems)
                .build()
        );

        return "Order placed successfully";
    }

    private OrderLineItem mapDtoToEntity(OrderLineItemsRequest itemsRequest) {
        return OrderLineItem.builder()
            .skuCode(itemsRequest.getSkuCode())
            .price(itemsRequest.getPrice())
            .quantity(itemsRequest.getQuantity())
            .build();
    }
}
