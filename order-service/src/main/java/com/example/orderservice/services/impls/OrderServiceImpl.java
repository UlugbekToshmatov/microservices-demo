package com.example.orderservice.services.impls;

import com.example.orderservice.dto.OrderLineItemsRequest;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderLineItem;
import com.example.orderservice.repositories.OrderRepository;
import com.example.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void placeOrder(OrderRequest request) {
        List<OrderLineItem> orderLineItems = request.getOrderLineItemsRequests().stream()
            .map(this::mapDtoToEntity).toList();

        orderRepository.save(
            Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItems(orderLineItems)
                .build()
        );
    }

    private OrderLineItem mapDtoToEntity(OrderLineItemsRequest itemsRequest) {
        return OrderLineItem.builder()
            .skuCode(itemsRequest.getSkuCode())
            .price(itemsRequest.getPrice())
            .quantity(itemsRequest.getQuantity())
            .build();
    }
}
