package com.example.inventoryservice.services;


import com.example.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    Boolean isInStock(String skuCode);
    List<InventoryResponse> isInStock(List<String> skuCode);
}
