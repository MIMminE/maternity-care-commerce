package com.portfolio.maternity.domain.pregnancy;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "pregnancy_profiles")
public class PregnancyProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PregnancyStatus status;

    private LocalDate expectedBirthDate;

    private LocalDate childBirthDate;

    private Integer pregnancyWeek;

    protected PregnancyProfile() {
    }

    public PregnancyProfile(
            Member member,
            PregnancyStatus status,
            LocalDate expectedBirthDate,
            LocalDate childBirthDate,
            Integer pregnancyWeek
    ) {
        this.member = member;
        this.status = status;
        this.expectedBirthDate = expectedBirthDate;
        this.childBirthDate = childBirthDate;
        this.pregnancyWeek = pregnancyWeek;
    }

    public void update(
            PregnancyStatus status,
            LocalDate expectedBirthDate,
            LocalDate childBirthDate,
            Integer pregnancyWeek
    ) {
        this.status = status;
        this.expectedBirthDate = expectedBirthDate;
        this.childBirthDate = childBirthDate;
        this.pregnancyWeek = pregnancyWeek;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public PregnancyStatus getStatus() {
        return status;
    }

    public LocalDate getExpectedBirthDate() {
        return expectedBirthDate;
    }

    public LocalDate getChildBirthDate() {
        return childBirthDate;
    }

    public Integer getPregnancyWeek() {
        return pregnancyWeek;
    }
}
