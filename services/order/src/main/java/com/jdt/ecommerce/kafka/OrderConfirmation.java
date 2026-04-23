package com.jdt.ecommerce.kafka;

import com.jdt.ecommerce.customer.CustomerResponse;
import com.jdt.ecommerce.order.PaymentMethod;
import com.jdt.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
