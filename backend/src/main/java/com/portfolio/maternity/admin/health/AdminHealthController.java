package com.portfolio.maternity.admin.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/v1/health")
public class AdminHealthController {

    @GetMapping
    public String health() {
        return "admin-api ok";
    }
}

