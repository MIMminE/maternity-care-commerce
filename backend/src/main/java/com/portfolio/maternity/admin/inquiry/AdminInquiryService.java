package com.portfolio.maternity.admin.inquiry;

import com.portfolio.maternity.client.inquiry.InquiryResponse;
import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.audit.AuditAction;
import com.portfolio.maternity.domain.audit.AuditLog;
import com.portfolio.maternity.domain.audit.AuditLogRepository;
import com.portfolio.maternity.domain.inquiry.Inquiry;
import com.portfolio.maternity.domain.inquiry.InquiryRepository;
import com.portfolio.maternity.domain.member.Member;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminInquiryService {

    private final InquiryRepository inquiryRepository;
    private final AuditLogRepository auditLogRepository;

    public AdminInquiryService(
            InquiryRepository inquiryRepository,
            AuditLogRepository auditLogRepository
    ) {
        this.inquiryRepository = inquiryRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(readOnly = true)
    public List<InquiryResponse> findInquiries() {
        return inquiryRepository.findAll()
                .stream()
                .map(InquiryResponse::from)
                .toList();
    }

    @Transactional
    public InquiryResponse update(
            Long adminUserId,
            Long inquiryId,
            AdminInquiryUpdateRequest request,
            String reason
    ) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("문의를 찾을 수 없습니다."));
        inquiry.updateStatus(request.status());
        auditLogRepository.save(new AuditLog(
                AdminUser.reference(adminUserId),
                Member.reference(inquiry.getMember().getId()),
                AuditAction.UPDATE_INQUIRY,
                reason
        ));
        return InquiryResponse.from(inquiry);
    }
}

