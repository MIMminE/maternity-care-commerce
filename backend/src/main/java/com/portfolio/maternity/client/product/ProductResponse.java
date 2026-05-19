package com.portfolio.maternity.client.product;

import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductStatus;
import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String category,
        BigDecimal price,
        int stockQuantity,
        ProductStatus status
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getStatus()
        );
    }
}

