package com.portfolio.maternity.global.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final byte[] secret;
    private final long expirationSeconds;

    public JwtTokenProvider(
            @Value("${security.jwt.secret:local-development-secret-key-change-me}") String secret,
            @Value("${security.jwt.expiration-seconds:7200}") long expirationSeconds
    ) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationSeconds;
    }

    public String issue(AuthPrincipal principal) {
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        long expiresAt = Instant.now().getEpochSecond() + expirationSeconds;
        String payload = "{"
                + "\"sub\":\"" + principal.id() + "\","
                + "\"email\":\"" + principal.email() + "\","
                + "\"type\":\"" + principal.type().name() + "\","
                + "\"role\":\"" + principal.role() + "\","
                + "\"exp\":" + expiresAt
                + "}";

        String encodedHeader = base64Url(header.getBytes(StandardCharsets.UTF_8));
        String encodedPayload = base64Url(payload.getBytes(StandardCharsets.UTF_8));
        String signature = sign(encodedHeader + "." + encodedPayload);
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    public AuthPrincipal parse(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token");
        }

        String expectedSignature = sign(parts[0] + "." + parts[1]);
        if (!constantTimeEquals(expectedSignature, parts[2])) {
            throw new IllegalArgumentException("Invalid token signature");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        long expiresAt = Long.parseLong(extractNumber(payload, "exp"));
        if (expiresAt < Instant.now().getEpochSecond()) {
            throw new IllegalArgumentException("Expired token");
        }

        return new AuthPrincipal(
                Long.parseLong(extractString(payload, "sub")),
                extractString(payload, "email"),
                PrincipalType.valueOf(extractString(payload, "type")),
                extractString(payload, "role")
        );
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return base64Url(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign token", e);
        }
    }

    private String base64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String extractString(String json, String key) {
        String marker = "\"" + key + "\":\"";
        int start = json.indexOf(marker);
        if (start < 0) {
            throw new IllegalArgumentException("Missing claim: " + key);
        }
        int valueStart = start + marker.length();
        int valueEnd = json.indexOf("\"", valueStart);
        return json.substring(valueStart, valueEnd);
    }

    private String extractNumber(String json, String key) {
        String marker = "\"" + key + "\":";
        int start = json.indexOf(marker);
        if (start < 0) {
            throw new IllegalArgumentException("Missing claim: " + key);
        }
        int valueStart = start + marker.length();
        int valueEnd = json.indexOf(",", valueStart);
        if (valueEnd < 0) {
            valueEnd = json.indexOf("}", valueStart);
        }
        return json.substring(valueStart, valueEnd);
    }

    private boolean constantTimeEquals(String left, String right) {
        if (left.length() != right.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < left.length(); i++) {
            result |= left.charAt(i) ^ right.charAt(i);
        }
        return result == 0;
    }
}

