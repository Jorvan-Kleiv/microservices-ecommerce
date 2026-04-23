package com.jdt.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(request));
    }
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchase(
            @RequestBody @Valid List<ProductPurchaseRequest> requests
    )
    {
        return ResponseEntity.ok(service.purchaseProduct(requests));
    }
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable("product-id") Integer productId
    )
    {
        return ResponseEntity.ok(service.findById(productId));
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        return ResponseEntity.ok(service.findAll());
    }

}
