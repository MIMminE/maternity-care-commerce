package com.portfolio.maternity.client.me;

import com.portfolio.maternity.global.security.AuthPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/me")
public class ClientMeController {

    @GetMapping
    public ClientMeResponse me(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return new ClientMeResponse(principal.id(), principal.email());
    }
}

