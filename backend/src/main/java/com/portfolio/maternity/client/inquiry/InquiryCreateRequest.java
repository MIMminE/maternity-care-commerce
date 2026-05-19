package com.portfolio.maternity.client.inquiry;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InquiryCreateRequest(
        Long productId,
        @NotBlank @Size(max = 160) String title,
        @NotBlank @Size(max = 1000) String body
) {
}

