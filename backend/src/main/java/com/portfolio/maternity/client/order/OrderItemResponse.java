package com.portfolio.maternity.client.order;

import com.portfolio.maternity.domain.order.OrderItem;
import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineAmount
) {
    public static OrderItemResponse from(OrderItem orderItem) {
        BigDecimal lineAmount = orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
        return new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                lineAmount
        );
    }
}

