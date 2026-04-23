package com.jdt.ecommerce.kafka;

import com.jdt.ecommerce.email.EmailService;
import com.jdt.ecommerce.kafka.order.OrderConfirmation;
import com.jdt.ecommerce.kafka.payment.PaymentConfirmation;
import com.jdt.ecommerce.notification.Notification;
import com.jdt.ecommerce.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.jdt.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;
import static com.jdt.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void paymentSuccessConfirmation(PaymentConfirmation  paymentConfirmation) throws MessagingException {
        log.info("Received payment confirmation: {}", paymentConfirmation);
        repository.save(
                Notification.builder()
                        .paymentConfirmation(paymentConfirmation)
                        .type(PAYMENT_CONFIRMATION)
                        .notifiedAt(LocalDateTime.now())
                        .build()
        );
        emailService.sendPaymentSucceed(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName(),
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }
    @KafkaListener(topics = "order-topic")
    public void orderSuccessConfirmation(OrderConfirmation  orderConfirmation) throws MessagingException {
        log.info("Received order confirmation: {}", orderConfirmation);
        repository.save(
                Notification.builder()
                        .orderConfirmation(orderConfirmation)
                        .type(ORDER_CONFIRMATION)
                        .notifiedAt(LocalDateTime.now())
                        .build()
        );
        this.emailService.orderConfirmationEmail(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }


}
