package com.portfolio.maternity.admin.member;

import com.portfolio.maternity.client.pregnancy.PregnancyProfileResponse;
import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.audit.AuditAction;
import com.portfolio.maternity.domain.audit.AuditLog;
import com.portfolio.maternity.domain.audit.AuditLogRepository;
import com.portfolio.maternity.domain.member.Member;
import com.portfolio.maternity.domain.member.MemberRepository;
import com.portfolio.maternity.domain.pregnancy.PregnancyProfileRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminMemberService {

    private static final int DEFAULT_PAGE_SIZE = 30;

    private final MemberRepository memberRepository;
    private final PregnancyProfileRepository pregnancyProfileRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminMemberService(
            MemberRepository memberRepository,
            PregnancyProfileRepository pregnancyProfileRepository,
            AuditLogRepository auditLogRepository
    ) {
        this.memberRepository = memberRepository;
        this.pregnancyProfileRepository = pregnancyProfileRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(readOnly = true)
    public List<AdminMemberSummaryResponse> findMembers(int page) {
        return memberRepository.findAll(PageRequest.of(page, DEFAULT_PAGE_SIZE))
                .stream()
                .map(AdminMemberSummaryResponse::from)
                .toList();
    }

    @Transactional
    public AdminMemberDetailResponse findMember(Long adminUserId, Long memberId, String reason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        PregnancyProfileResponse pregnancyProfile = pregnancyProfileRepository.findByMemberId(memberId)
                .map(PregnancyProfileResponse::from)
                .orElse(null);

        auditLogRepository.save(new AuditLog(
                AdminUser.reference(adminUserId),
                Member.reference(memberId),
                AuditAction.VIEW_MEMBER,
                reason
        ));

        if (pregnancyProfile != null) {
            auditLogRepository.save(new AuditLog(
                    AdminUser.reference(adminUserId),
                    Member.reference(memberId),
                    AuditAction.VIEW_PREGNANCY_PROFILE,
                    reason
            ));
        }

        return AdminMemberDetailResponse.of(member, pregnancyProfile);
    }
}

