package com.portfolio.maternity.domain.consent;

import com.portfolio.maternity.domain.common.BaseTimeEntity;
import com.portfolio.maternity.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "consent_histories")
public class ConsentHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ConsentType consentType;

    @Column(nullable = false)
    private boolean agreed;

    @Column(nullable = false)
    private LocalDateTime agreedAt;

    protected ConsentHistory() {
    }

    public ConsentHistory(Member member, ConsentType consentType, boolean agreed, LocalDateTime agreedAt) {
        this.member = member;
        this.consentType = consentType;
        this.agreed = agreed;
        this.agreedAt = agreedAt;
    }
}
