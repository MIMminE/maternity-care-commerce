package com.portfolio.maternity.client.consent;

import com.portfolio.maternity.domain.consent.ConsentHistoryRepository;
import com.portfolio.maternity.global.security.AuthPrincipal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/consents/me")
public class ClientConsentController {

    private final ConsentHistoryRepository consentHistoryRepository;

    public ClientConsentController(ConsentHistoryRepository consentHistoryRepository) {
        this.consentHistoryRepository = consentHistoryRepository;
    }

    @GetMapping
    public List<ConsentHistoryResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return consentHistoryRepository.findByMemberIdOrderByAgreedAtDesc(principal.id())
                .stream()
                .map(ConsentHistoryResponse::from)
                .toList();
    }
}

