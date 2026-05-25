package com.portfolio.maternity.admin.audit;

import com.portfolio.maternity.domain.audit.AuditAction;
import com.portfolio.maternity.domain.audit.AuditLog;
import java.time.LocalDateTime;

public record AuditLogResponse(
        Long id,
        String adminEmail,
        Long targetMemberId,
        AuditAction action,
        String reason,
        LocalDateTime createdAt
) {
    public static AuditLogResponse from(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getAdminEmail(),
                auditLog.getTargetMemberId(),
                auditLog.getAction(),
                auditLog.getReason(),
                auditLog.getCreatedAt()
        );
    }
}
