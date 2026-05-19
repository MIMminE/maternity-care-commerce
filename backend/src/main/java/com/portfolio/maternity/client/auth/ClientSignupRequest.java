package com.portfolio.maternity.client.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientSignupRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 8, max = 60) String password,
        @NotBlank @Size(max = 80) String name,
        @Size(max = 30) String phoneNumber,
        @AssertTrue boolean termsAgreed,
        @AssertTrue boolean privacyAgreed,
        @AssertTrue boolean sensitiveInformationAgreed,
        boolean marketingAgreed
) {
}

