package com.example.inventoryservice.services.impls;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repositories.InventoryRepository;
import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Boolean isInStock(String skuCode) {
        return inventoryRepository.existsBySkuCodeAndDeletedFalse(skuCode);
    }

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findAllBySkuCodeInAndDeletedFalse(skuCode).stream()
            .map(
                inventory -> InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
            ).toList();
    }
}
