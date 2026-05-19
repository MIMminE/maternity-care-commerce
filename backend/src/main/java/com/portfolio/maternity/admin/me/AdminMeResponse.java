package com.portfolio.maternity.admin.me;

public record AdminMeResponse(
        Long adminUserId,
        String email,
        String role
) {
}

