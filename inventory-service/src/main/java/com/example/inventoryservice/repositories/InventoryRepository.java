package com.example.inventoryservice.repositories;

import com.example.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Boolean existsBySkuCodeAndDeletedFalse(String skuCode);
    List<Inventory> findAllByDeletedFalse();
    List<Inventory> findAllBySkuCodeInAndDeletedFalse(List<String> skuCode);
}
