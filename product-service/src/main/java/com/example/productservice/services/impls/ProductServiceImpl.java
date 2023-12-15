package com.example.productservice.services.impls;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void create(ProductRequest request) {
        Product savedProduct = productRepository.save(
            Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .createdDate(new Date())
                .modifiedDate(new Date())
                .deleted(Boolean.FALSE)
                .build()
        );

        log.info("Product registered with id={}", savedProduct.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.getAllByDeletedFalse().stream()
            .map(this::mapProductToResponse).toList();
    }

    private ProductResponse mapProductToResponse(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .build();
    }
}
