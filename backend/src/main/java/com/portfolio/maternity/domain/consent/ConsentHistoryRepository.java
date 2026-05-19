package com.portfolio.maternity.domain.consent;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentHistoryRepository extends JpaRepository<ConsentHistory, Long> {

    List<ConsentHistory> findByMemberIdOrderByAgreedAtDesc(Long memberId);
}
