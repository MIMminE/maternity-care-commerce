package com.portfolio.maternity.client.cart;

import com.portfolio.maternity.domain.cart.CartItem;
import com.portfolio.maternity.domain.cart.CartItemRepository;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientCartService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public ClientCartService(
            CartItemRepository cartItemRepository,
            MemberRepository memberRepository,
            ProductRepository productRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<CartItemResponse> findMine(Long memberId) {
        return cartItemRepository.findByMemberIdOrderByIdDesc(memberId)
                .stream()
                .map(CartItemResponse::from)
                .toList();
    }

    @Transactional
    public CartItemResponse addOrUpdate(Long memberId, CartItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        if (!product.isOnSale()) {
            throw new IllegalArgumentException("판매 중인 상품만 장바구니에 담을 수 있습니다.");
        }
        if (product.getStockQuantity() < request.quantity()) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }

        CartItem cartItem = cartItemRepository.findByMemberIdAndProductId(memberId, request.productId())
                .map(existing -> {
                    existing.changeQuantity(request.quantity());
                    return existing;
                })
                .orElseGet(() -> {
                    Member member = memberRepository.findById(memberId)
                            .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
                    return cartItemRepository.save(new CartItem(member, product, request.quantity()));
                });
        return CartItemResponse.from(cartItem);
    }
}

