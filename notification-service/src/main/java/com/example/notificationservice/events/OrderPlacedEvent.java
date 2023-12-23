package com.example.notificationservice.events;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderPlacedEvent {
    private String orderNumber;
}
