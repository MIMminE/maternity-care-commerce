package com.portfolio.maternity.client.product;

import com.portfolio.maternity.domain.product.ProductRepository;
import com.portfolio.maternity.domain.product.ProductStatus;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/products")
public class ClientProductController {

    private final ProductRepository productRepository;

    public ClientProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductResponse> findProducts() {
        return productRepository.findByStatusOrderByIdDesc(ProductStatus.ON_SALE)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{productId}")
    public ProductResponse findProduct(@PathVariable Long productId) {
        return productRepository.findById(productId)
                .filter(product -> product.getStatus() == ProductStatus.ON_SALE)
                .map(ProductResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("판매 중인 상품을 찾을 수 없습니다."));
    }
}

