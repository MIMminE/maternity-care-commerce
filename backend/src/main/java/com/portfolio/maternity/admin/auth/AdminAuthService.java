package com.portfolio.maternity.admin.auth;

import com.portfolio.maternity.client.auth.AuthTokenResponse;
import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.adminuser.AdminUserRepository;
import com.portfolio.maternity.global.security.AuthPrincipal;
import com.portfolio.maternity.global.security.JwtTokenProvider;
import com.portfolio.maternity.global.security.PrincipalType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminAuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AdminAuthService(
            AdminUserRepository adminUserRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider
    ) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional(readOnly = true)
    public AuthTokenResponse login(AdminLoginRequest request) {
        AdminUser adminUser = adminUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!adminUser.isActive() || !passwordEncoder.matches(request.password(), adminUser.getPasswordHash())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String role = adminUser.getRole().name();
        String token = tokenProvider.issue(new AuthPrincipal(
                adminUser.getId(),
                adminUser.getEmail(),
                PrincipalType.ADMIN,
                role
        ));
        return AuthTokenResponse.bearer(token, adminUser.getId(), adminUser.getEmail(), role);
    }
}

