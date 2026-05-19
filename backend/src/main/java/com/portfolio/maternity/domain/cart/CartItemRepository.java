package com.portfolio.maternity.domain.cart;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByMemberIdOrderByIdDesc(Long memberId);

    Optional<CartItem> findByMemberIdAndProductId(Long memberId, Long productId);

    void deleteByMemberId(Long memberId);
}

