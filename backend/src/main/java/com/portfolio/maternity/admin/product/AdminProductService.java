package com.portfolio.maternity.admin.product;

import com.portfolio.maternity.client.product.ProductResponse;
import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminProductService {

    private final ProductRepository productRepository;

    public AdminProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Transactional
    public ProductResponse create(AdminProductRequest request) {
        Product product = productRepository.save(new Product(
                request.name(),
                request.category(),
                request.price(),
                request.stockQuantity(),
                request.status()
        ));
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse update(Long productId, AdminProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        product.update(
                request.name(),
                request.category(),
                request.price(),
                request.stockQuantity(),
                request.status()
        );
        return ProductResponse.from(product);
    }
}

