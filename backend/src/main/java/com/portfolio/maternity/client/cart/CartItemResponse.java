package com.portfolio.maternity.client.cart;

import com.portfolio.maternity.domain.cart.CartItem;
import java.math.BigDecimal;

public record CartItemResponse(
        Long cartItemId,
        Long productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineAmount
) {
    public static CartItemResponse from(CartItem cartItem) {
        BigDecimal lineAmount = cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getPrice(),
                cartItem.getQuantity(),
                lineAmount
        );
    }
}

