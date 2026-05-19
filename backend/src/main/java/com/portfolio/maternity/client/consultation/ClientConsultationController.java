package com.portfolio.maternity.client.consultation;

import com.portfolio.maternity.global.security.AuthPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/consultations")
public class ClientConsultationController {

    private final ClientConsultationService clientConsultationService;

    public ClientConsultationController(ClientConsultationService clientConsultationService) {
        this.clientConsultationService = clientConsultationService;
    }

    @GetMapping
    public List<ConsultationResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientConsultationService.findMine(principal.id());
    }

    @PostMapping
    public ConsultationResponse create(
            Authentication authentication,
            @Valid @RequestBody ConsultationCreateRequest request
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientConsultationService.create(principal.id(), request);
    }
}

