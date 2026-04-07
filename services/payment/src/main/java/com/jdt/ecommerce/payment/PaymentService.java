package com.jdt.ecommerce.payment;

import com.jdt.ecommerce.kafka.PaymentConfirmation;
import com.jdt.ecommerce.kafka.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final PaymentProducer producer;

    public Integer initPayment(PaymentRequest request) {
        Payment payment = repository.save(mapper.toPayment(request));
        producer.sendPaymentConfirmation(
                new PaymentConfirmation(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
