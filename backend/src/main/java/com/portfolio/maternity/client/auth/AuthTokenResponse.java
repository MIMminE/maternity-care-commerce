package com.portfolio.maternity.client.auth;

public record AuthTokenResponse(
        String accessToken,
        String tokenType,
        Long principalId,
        String email,
        String role
) {
    public static AuthTokenResponse bearer(String accessToken, Long principalId, String email, String role) {
        return new AuthTokenResponse(accessToken, "Bearer", principalId, email, role);
    }
}

