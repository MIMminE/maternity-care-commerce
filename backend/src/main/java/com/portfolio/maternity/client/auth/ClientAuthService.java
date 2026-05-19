package com.portfolio.maternity.client.auth;

import com.portfolio.maternity.domain.consent.ConsentHistory;
import com.portfolio.maternity.domain.consent.ConsentHistoryRepository;
import com.portfolio.maternity.domain.consent.ConsentType;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.global.security.AuthPrincipal;
import com.portfolio.maternity.global.security.JwtTokenProvider;
import com.portfolio.maternity.global.security.PrincipalType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientAuthService {

    private final MemberRepository memberRepository;
    private final ConsentHistoryRepository consentHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public ClientAuthService(
            MemberRepository memberRepository,
            ConsentHistoryRepository consentHistoryRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider tokenProvider
    ) {
        this.memberRepository = memberRepository;
        this.consentHistoryRepository = consentHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public AuthTokenResponse signup(ClientSignupRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = memberRepository.save(new Member(
                request.email(),
                request.name(),
                request.phoneNumber(),
                passwordEncoder.encode(request.password())
        ));
        LocalDateTime now = LocalDateTime.now();
        consentHistoryRepository.saveAll(List.of(
                new ConsentHistory(member, ConsentType.TERMS, request.termsAgreed(), now),
                new ConsentHistory(member, ConsentType.PRIVACY, request.privacyAgreed(), now),
                new ConsentHistory(member, ConsentType.SENSITIVE_INFORMATION, request.sensitiveInformationAgreed(), now),
                new ConsentHistory(member, ConsentType.MARKETING, request.marketingAgreed(), now)
        ));

        return issue(member);
    }

    @Transactional(readOnly = true)
    public AuthTokenResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(request.password(), member.getPasswordHash())) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return issue(member);
    }

    private AuthTokenResponse issue(Member member) {
        String token = tokenProvider.issue(new AuthPrincipal(
                member.getId(),
                member.getEmail(),
                PrincipalType.MEMBER,
                "MEMBER"
        ));
        return AuthTokenResponse.bearer(token, member.getId(), member.getEmail(), "MEMBER");
    }
}

