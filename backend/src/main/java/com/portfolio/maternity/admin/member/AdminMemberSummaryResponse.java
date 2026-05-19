package com.portfolio.maternity.admin.member;

import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberStatus;

public record AdminMemberSummaryResponse(
        Long id,
        String email,
        String name,
        String phoneNumber,
        MemberStatus status
) {
    public static AdminMemberSummaryResponse from(Member member) {
        return new AdminMemberSummaryResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber(),
                member.getStatus()
        );
    }
}

