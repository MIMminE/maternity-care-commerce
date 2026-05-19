package com.portfolio.maternity.admin.consultation;

import com.portfolio.maternity.domain.consultation.ConsultationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminConsultationUpdateRequest(
        @NotNull ConsultationStatus status,
        @Size(max = 1000) String internalMemo
) {
}

