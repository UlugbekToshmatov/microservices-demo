package com.example.inventoryservice.services.impls;

import com.example.inventoryservice.repositories.InventoryRepository;
import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Boolean isInStock(String skuCode) {
        return inventoryRepository.existsBySkuCodeAndDeletedFalse(skuCode);
    }
}
