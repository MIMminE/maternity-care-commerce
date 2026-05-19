package com.portfolio.maternity.client.pregnancy;

import com.portfolio.maternity.global.security.AuthPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/pregnancy-profile/me")
public class ClientPregnancyProfileController {

    private final ClientPregnancyProfileService pregnancyProfileService;

    public ClientPregnancyProfileController(ClientPregnancyProfileService pregnancyProfileService) {
        this.pregnancyProfileService = pregnancyProfileService;
    }

    @GetMapping
    public ResponseEntity<PregnancyProfileResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return pregnancyProfileService.findMine(principal.id())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PutMapping
    public PregnancyProfileResponse upsertMine(
            Authentication authentication,
            @Valid @RequestBody PregnancyProfileRequest request
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return pregnancyProfileService.upsertMine(principal.id(), request);
    }
}

