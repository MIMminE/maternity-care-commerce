package com.portfolio.maternity.admin.me;

import com.portfolio.maternity.global.security.AuthPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/me")
public class AdminMeController {

    @GetMapping
    public AdminMeResponse me(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return new AdminMeResponse(principal.id(), principal.email(), principal.role());
    }
}

