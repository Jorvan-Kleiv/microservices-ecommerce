package com.jdt.ecommerce.notification;

import com.jdt.ecommerce.kafka.order.OrderConfirmation;
import com.jdt.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Notification {
    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notifiedAt;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;
}
