package com.jdt.ecommerce.orderline;

import com.jdt.ecommerce.order.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .productId(request.productId())
                .order(Order.builder()
                        .id(request.orderId())
                        .build())
                .build();
    }

    public OrderLineResponse fromOrderLine(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(),  orderLine.getQuantity());
    }
}
