package com.example.productservice.services;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void create(ProductRequest request);

    List<ProductResponse> getAllProducts();
}
