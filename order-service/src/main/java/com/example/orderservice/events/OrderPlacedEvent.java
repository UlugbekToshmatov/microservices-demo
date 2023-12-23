package com.example.orderservice.events;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderPlacedEvent {
    private String orderNumber;
}
