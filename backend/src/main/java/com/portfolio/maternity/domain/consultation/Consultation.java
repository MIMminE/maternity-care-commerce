package com.portfolio.maternity.domain.consultation;

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

@Entity
@Table(name = "consultations")
public class Consultation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(nullable = false, length = 1000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConsultationStatus status = ConsultationStatus.REQUESTED;

    @Column(length = 1000)
    private String internalMemo;

    protected Consultation() {
    }

    public Consultation(Member member, String title, String body) {
        this.member = member;
        this.title = title;
        this.body = body;
        this.status = ConsultationStatus.REQUESTED;
    }

    public void updateStatus(ConsultationStatus status, String internalMemo) {
        this.status = status;
        this.internalMemo = internalMemo;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public ConsultationStatus getStatus() {
        return status;
    }

    public String getInternalMemo() {
        return internalMemo;
    }
}
