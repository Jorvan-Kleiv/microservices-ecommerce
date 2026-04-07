package com.jdt.ecommerce.order;

import com.jdt.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be precised")
        @NotBlank(message = "Customer should be precised")
        @NotEmpty(message = "Customer should be precised")
        String customerId,
        @NotEmpty(message = "At least one product should be purchased")
        List<PurchaseRequest> products
) {
}
