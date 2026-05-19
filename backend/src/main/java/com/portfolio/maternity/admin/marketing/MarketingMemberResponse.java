package com.portfolio.maternity.admin.marketing;

import com.portfolio.maternity.domain.member.Member;

public record MarketingMemberResponse(
        Long memberId,
        String email,
        String name,
        String phoneNumber
) {
    public static MarketingMemberResponse from(Member member) {
        return new MarketingMemberResponse(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhoneNumber()
        );
    }
}

