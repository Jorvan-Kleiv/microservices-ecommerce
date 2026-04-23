package com.jdt.ecommerce.email;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EmailTemplates {
    PAYMENT_SUCCEED("payment-succeed.html", "Payment successfully proceed"),
    ORDER_SUCCEED("order-succeed.html", "Order Confirmation")
    ;
    @Getter
    private final String template;
    @Getter
    private final String subject;
}
