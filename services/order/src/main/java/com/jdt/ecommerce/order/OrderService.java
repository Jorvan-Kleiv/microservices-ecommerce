package com.jdt.ecommerce.order;

import com.jdt.ecommerce.customer.CustomerClient;
import com.jdt.ecommerce.exception.BusinessException;
import com.jdt.ecommerce.kafka.OrderConfirmation;
import com.jdt.ecommerce.kafka.OrderProducer;
import com.jdt.ecommerce.orderline.OrderLineRequest;
import com.jdt.ecommerce.orderline.OrderLineService;
import com.jdt.ecommerce.payment.PaymentClient;
import com.jdt.ecommerce.payment.PaymentRequest;
import com.jdt.ecommerce.product.ProductClient;
import com.jdt.ecommerce.product.PurchaseRequest;
import com.jdt.ecommerce.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final OrderProducer producer;
    private final OrderMapper mapper;
    public Integer createOrder(OrderRequest request) {
        // check the customer
        var customer = customerClient.getCustomer(request.customerId())
                .orElseThrow(() -> new BusinessException("Unable to place an order: no customer found with id " + request.customerId()));
        List<PurchaseResponse> purchaseResponses = this.productClient.purchaseProduct(request.products());
        Order order = this.repository.save(this.mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest : request.products()) {
            this.orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var payment = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        this.paymentClient.requestPayment(payment);
        this.producer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseResponses
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find order with id " + orderId));
    }
}
