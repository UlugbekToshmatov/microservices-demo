package com.example.inventoryservice.controllers;

import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("{sku-code}")
    public ResponseEntity<Boolean> isInStock(@PathVariable("sku-code") String skuCode) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode) {
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
