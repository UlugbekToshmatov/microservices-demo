package com.example.inventoryservice.services.impls;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.repositories.InventoryRepository;
import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    public Boolean isInStock(String skuCode) {
        return inventoryRepository.existsBySkuCodeAndDeletedFalse(skuCode);
    }

    @Override
    // Whatever exception is thrown in the method, @SneakyThrows consumes it
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        log.info("Waiting for inventory-service started");
        Thread.sleep(3000);
        log.info("Waiting for inventory-service finished");

        return inventoryRepository.findAllBySkuCodeInAndDeletedFalse(skuCode).stream()
            .map(
                inventory -> InventoryResponse.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build()
            ).toList();
    }
}
