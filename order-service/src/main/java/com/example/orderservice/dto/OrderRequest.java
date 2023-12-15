package com.example.orderservice.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest {
    private List<OrderLineItemsRequest> orderLineItemsRequests;
}
