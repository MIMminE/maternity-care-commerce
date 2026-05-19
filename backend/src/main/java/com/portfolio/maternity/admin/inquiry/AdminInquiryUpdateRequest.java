package com.portfolio.maternity.admin.inquiry;

import com.portfolio.maternity.domain.inquiry.InquiryStatus;
import jakarta.validation.constraints.NotNull;

public record AdminInquiryUpdateRequest(
        @NotNull InquiryStatus status
) {
}

