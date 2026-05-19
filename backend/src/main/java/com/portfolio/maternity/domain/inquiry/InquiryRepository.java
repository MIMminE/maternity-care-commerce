package com.portfolio.maternity.domain.inquiry;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    List<Inquiry> findByMemberIdOrderByIdDesc(Long memberId);

    long countByStatus(InquiryStatus status);
}
