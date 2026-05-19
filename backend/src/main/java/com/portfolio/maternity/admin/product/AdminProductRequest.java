package com.portfolio.maternity.admin.product;

import com.portfolio.maternity.domain.product.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record AdminProductRequest(
        @NotBlank @Size(max = 160) String name,
        @NotBlank @Size(max = 80) String category,
        @NotNull @DecimalMin("0.00") BigDecimal price,
        @Min(0) int stockQuantity,
        @NotNull ProductStatus status
) {
}

