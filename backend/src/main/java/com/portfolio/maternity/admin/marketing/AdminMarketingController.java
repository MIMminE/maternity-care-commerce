package com.portfolio.maternity.admin.marketing;

import com.portfolio.maternity.global.security.AuthPrincipal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/marketing")
public class AdminMarketingController {

    private final AdminMarketingService adminMarketingService;

    public AdminMarketingController(AdminMarketingService adminMarketingService) {
        this.adminMarketingService = adminMarketingService;
    }

    @GetMapping("/members")
    public List<MarketingMemberResponse> findMarketingAgreedMembers(
            Authentication authentication,
            @RequestHeader(name = "X-Audit-Reason", defaultValue = "마케팅 동의 고객 조회") String reason
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return adminMarketingService.findMarketingAgreedMembers(principal.id(), reason);
    }
}

