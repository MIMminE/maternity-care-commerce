package com.portfolio.maternity.admin.product;

import com.portfolio.maternity.client.product.ProductResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @GetMapping
    public List<ProductResponse> findProducts() {
        return adminProductService.findProducts();
    }

    @PostMapping
    public ProductResponse create(@Valid @RequestBody AdminProductRequest request) {
        return adminProductService.create(request);
    }

    @PatchMapping("/{productId}")
    public ProductResponse update(
            @PathVariable Long productId,
            @Valid @RequestBody AdminProductRequest request
    ) {
        return adminProductService.update(productId, request);
    }
}

