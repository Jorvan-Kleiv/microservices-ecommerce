package com.jdt.ecommerce.payment;

import com.jdt.ecommerce.customer.CustomerResponse;
import com.jdt.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
