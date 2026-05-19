package com.portfolio.maternity.admin.marketing;

import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.audit.AuditAction;
import com.portfolio.maternity.domain.audit.AuditLog;
import com.portfolio.maternity.domain.audit.AuditLogRepository;
import com.portfolio.maternity.domain.member.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminMarketingService {

    private final MemberRepository memberRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminMarketingService(
            MemberRepository memberRepository,
            AuditLogRepository auditLogRepository
    ) {
        this.memberRepository = memberRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional
    public List<MarketingMemberResponse> findMarketingAgreedMembers(Long adminUserId, String reason) {
        auditLogRepository.save(new AuditLog(
                AdminUser.reference(adminUserId),
                null,
                AuditAction.EXPORT_MARKETING_TARGETS,
                reason
        ));
        return memberRepository.findMarketingAgreedMembers()
                .stream()
                .map(MarketingMemberResponse::from)
                .toList();
    }
}

