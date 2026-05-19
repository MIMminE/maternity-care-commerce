package com.portfolio.maternity.admin.inquiry;

import com.portfolio.maternity.client.inquiry.InquiryResponse;
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
@RequestMapping("/admin-api/v1/inquiries")
public class AdminInquiryController {

    private final AdminInquiryService adminInquiryService;

    public AdminInquiryController(AdminInquiryService adminInquiryService) {
        this.adminInquiryService = adminInquiryService;
    }

    @GetMapping
    public List<InquiryResponse> findInquiries() {
        return adminInquiryService.findInquiries();
    }

    @PatchMapping("/{inquiryId}/status")
    public InquiryResponse update(
            Authentication authentication,
            @PathVariable Long inquiryId,
            @Valid @RequestBody AdminInquiryUpdateRequest request,
            @RequestHeader(name = "X-Audit-Reason", defaultValue = "관리자 문의 상태 변경") String reason
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return adminInquiryService.update(principal.id(), inquiryId, request, reason);
    }
}

