package com.portfolio.maternity.client.consultation;

import com.portfolio.maternity.domain.consultation.Consultation;
import com.portfolio.maternity.domain.consultation.ConsultationStatus;

public record ConsultationResponse(
        Long id,
        Long memberId,
        String title,
        String body,
        ConsultationStatus status,
        String internalMemo
) {
    public static ConsultationResponse from(Consultation consultation) {
        return new ConsultationResponse(
                consultation.getId(),
                consultation.getMember().getId(),
                consultation.getTitle(),
                consultation.getBody(),
                consultation.getStatus(),
                consultation.getInternalMemo()
        );
    }
}

