package com.portfolio.maternity.domain.consultation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    List<Consultation> findByMemberIdOrderByIdDesc(Long memberId);

    long countByStatus(ConsultationStatus status);
}
