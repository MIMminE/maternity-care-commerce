package com.portfolio.maternity.admin.audit;

import com.portfolio.maternity.domain.audit.AuditLogRepository;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/audit-logs")
public class AdminAuditLogController {

    private final AuditLogRepository auditLogRepository;

    public AdminAuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping
    public List<AuditLogResponse> findAuditLogs(@RequestParam(defaultValue = "0") int page) {
        return auditLogRepository.findAll(
                        PageRequest.of(page, 30, Sort.by(Sort.Direction.DESC, "createdAt"))
                )
                .map(AuditLogResponse::from)
                .toList();
    }
}
