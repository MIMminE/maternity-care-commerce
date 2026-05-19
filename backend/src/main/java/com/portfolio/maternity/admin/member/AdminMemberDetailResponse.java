package com.portfolio.maternity.admin.member;

import com.portfolio.maternity.client.pregnancy.PregnancyProfileResponse;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberStatus;

public record AdminMemberDetailResponse(
        Long id,
        String email,
        String name,
        String phoneNumber,
        MemberStatus status,
        PregnancyProfileResponse pregnancyProfile
) {
    public static AdminMemberDetailResponse of(Member member, PregnancyProfileResponse pregnancyProfile) {
        return new AdminMemberDetailResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getStatus(),
                pregnancyProfile
        );
    }
}

