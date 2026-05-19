package com.portfolio.maternity.domain.member;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    @Query("""
            select count(distinct m.id)
            from Member m
            join ConsentHistory c on c.member = m
            where c.consentType = com.portfolio.maternity.domain.consent.ConsentType.MARKETING
              and c.agreed = true
            """)
    long countMarketingAgreedMembers();

    @Query("""
            select distinct m
            from Member m
            join ConsentHistory c on c.member = m
            where c.consentType = com.portfolio.maternity.domain.consent.ConsentType.MARKETING
              and c.agreed = true
            order by m.id desc
            """)
    List<Member> findMarketingAgreedMembers();
}
