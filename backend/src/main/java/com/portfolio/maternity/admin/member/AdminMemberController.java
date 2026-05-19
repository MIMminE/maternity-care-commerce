package com.portfolio.maternity.admin.member;

import com.portfolio.maternity.global.security.AuthPrincipal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/members")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    public AdminMemberController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    @GetMapping
    public List<AdminMemberSummaryResponse> findMembers(
            @RequestParam(defaultValue = "0") int page
    ) {
        return adminMemberService.findMembers(page);
    }

    @GetMapping("/{memberId}")
    public AdminMemberDetailResponse findMember(
            Authentication authentication,
            @PathVariable Long memberId,
            @RequestHeader(name = "X-Audit-Reason", defaultValue = "관리자 회원 상세 조회") String reason
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return adminMemberService.findMember(principal.id(), memberId, reason);
    }
}

