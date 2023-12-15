package com.example.inventoryservice.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private Integer quantity;
    private LocalDateTime createdDate /* = LocalDateTime.now()*/;
    private LocalDateTime modifiedDate /* = LocalDateTime.now()*/;
    private Boolean deleted = false;
}
