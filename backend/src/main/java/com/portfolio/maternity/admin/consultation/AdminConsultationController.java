package com.portfolio.maternity.admin.consultation;

import com.portfolio.maternity.client.consultation.ConsultationResponse;
import com.portfolio.maternity.global.security.AuthPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/consultations")
public class AdminConsultationController {

    private final AdminConsultationService adminConsultationService;

    public AdminConsultationController(AdminConsultationService adminConsultationService) {
        this.adminConsultationService = adminConsultationService;
    }

    @GetMapping
    public List<ConsultationResponse> findConsultations() {
        return adminConsultationService.findConsultations();
    }

    @PatchMapping("/{consultationId}/status")
    public ConsultationResponse update(
            Authentication authentication,
            @PathVariable Long consultationId,
            @Valid @RequestBody AdminConsultationUpdateRequest request,
            @RequestHeader(name = "X-Audit-Reason", defaultValue = "관리자 상담 상태 변경") String reason
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return adminConsultationService.update(principal.id(), consultationId, request, reason);
    }
}

