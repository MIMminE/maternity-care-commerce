package com.portfolio.maternity.global.security;

public record AuthPrincipal(
        Long id,
        String email,
        PrincipalType type,
        String role
) {
}

