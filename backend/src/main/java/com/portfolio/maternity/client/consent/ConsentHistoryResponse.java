package com.portfolio.maternity.client.consent;

import com.portfolio.maternity.domain.consent.ConsentHistory;
import com.portfolio.maternity.domain.consent.ConsentType;
import java.time.LocalDateTime;

public record ConsentHistoryResponse(
        ConsentType consentType,
        boolean agreed,
        LocalDateTime agreedAt
) {
    public static ConsentHistoryResponse from(ConsentHistory consentHistory) {
        return new ConsentHistoryResponse(
                consentHistory.getConsentType(),
                consentHistory.isAgreed(),
                consentHistory.getAgreedAt()
        );
    }
}

