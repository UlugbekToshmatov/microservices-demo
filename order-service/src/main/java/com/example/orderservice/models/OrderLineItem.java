package com.example.orderservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_line_items")
public class OrderLineItem extends BaseEntity {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
