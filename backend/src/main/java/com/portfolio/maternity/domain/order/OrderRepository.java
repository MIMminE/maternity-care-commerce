package com.portfolio.maternity.domain.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMemberIdOrderByIdDesc(Long memberId);
}

