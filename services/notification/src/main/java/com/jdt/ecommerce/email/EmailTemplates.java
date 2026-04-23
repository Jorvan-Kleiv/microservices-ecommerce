package com.jdt.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_SUCCEED("payment-succeed.html", "Payment successfully proceed"),
    ORDER_SUCCEED("order-succeed.html", "Order Confirmation")
    ;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    private final String template;
    private final String subject;
    public String getTemplate() { return template; }
    public String getSubject() { return subject; }
}
