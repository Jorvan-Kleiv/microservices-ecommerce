package com.jdt.ecommerce.product;

import com.jdt.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(ProductRequest request) {
        return repository.save(mapper.toEntity(request)).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> requests) {
        List<Integer> productIds = requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        List<Product> existingProducts = repository.findAllByIdInOrderById(productIds);
        if (existingProducts.isEmpty()) {
            throw new EntityNotFoundException("Product not found");
        }
        if (existingProducts.size() != productIds.size()) {
            throw new ProductPurchaseException("One or more product doesn't exist");
        }
        var storedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < existingProducts.size(); i++) {
            var product = existingProducts.get(i);
            var productRequest = storedRequests.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient quantity for product requested");
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toPurchased(product,productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(int productId) {
        return repository.findById(productId).map(mapper::toResponse).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
