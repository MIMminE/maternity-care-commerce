package com.portfolio.maternity.domain.pregnancy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PregnancyProfileRepository extends JpaRepository<PregnancyProfile, Long> {

    Optional<PregnancyProfile> findByMemberId(Long memberId);
}

