package com.portfolio.maternity.client.order;

import com.portfolio.maternity.domain.order.Order;
import com.portfolio.maternity.domain.order.OrderStatus;
import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderNumber,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponse> items
) {
    public static OrderResponse of(Order order, List<OrderItemResponse> items) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getTotalAmount(),
                items
        );
    }
}

