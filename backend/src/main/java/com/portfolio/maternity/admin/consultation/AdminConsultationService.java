package com.portfolio.maternity.admin.consultation;

import com.portfolio.maternity.client.consultation.ConsultationResponse;
import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.audit.AuditAction;
import com.portfolio.maternity.domain.audit.AuditLog;
import com.portfolio.maternity.domain.audit.AuditLogRepository;
import com.portfolio.maternity.domain.consultation.Consultation;
import com.portfolio.maternity.domain.consultation.ConsultationRepository;
import com.portfolio.maternity.domain.member.Member;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminConsultationService(
            ConsultationRepository consultationRepository,
            AuditLogRepository auditLogRepository
    ) {
        this.consultationRepository = consultationRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(readOnly = true)
    public List<ConsultationResponse> findConsultations() {
        return consultationRepository.findAll()
                .stream()
                .map(ConsultationResponse::from)
                .toList();
    }

    @Transactional
    public ConsultationResponse update(
            Long adminUserId,
            Long consultationId,
            AdminConsultationUpdateRequest request,
            String reason
    ) {
        Consultation consultation = consultationRepository.findById(consultationId)
                .orElseThrow(() -> new IllegalArgumentException("상담을 찾을 수 없습니다."));
        consultation.updateStatus(request.status(), request.internalMemo());
        auditLogRepository.save(new AuditLog(
                AdminUser.reference(adminUserId),
                Member.reference(consultation.getMember().getId()),
                AuditAction.UPDATE_CONSULTATION,
                reason
        ));
        return ConsultationResponse.from(consultation);
    }
}

