package com.portfolio.maternity.client.inquiry;

import com.portfolio.maternity.domain.inquiry.Inquiry;
import com.portfolio.maternity.domain.inquiry.InquiryStatus;

public record InquiryResponse(
        Long id,
        Long memberId,
        Long productId,
        String productName,
        String title,
        String body,
        InquiryStatus status
) {
    public static InquiryResponse from(Inquiry inquiry) {
        return new InquiryResponse(
                inquiry.getId(),
                inquiry.getMember().getId(),
                inquiry.getProduct() == null ? null : inquiry.getProduct().getId(),
                inquiry.getProduct() == null ? null : inquiry.getProduct().getName(),
                inquiry.getTitle(),
                inquiry.getBody(),
                inquiry.getStatus()
        );
    }
}

