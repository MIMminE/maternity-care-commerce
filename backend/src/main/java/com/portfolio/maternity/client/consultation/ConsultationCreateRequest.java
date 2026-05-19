package com.portfolio.maternity.client.consultation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ConsultationCreateRequest(
        @NotBlank @Size(max = 160) String title,
        @NotBlank @Size(max = 1000) String body
) {
}

