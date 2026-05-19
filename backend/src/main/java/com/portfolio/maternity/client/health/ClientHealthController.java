package com.portfolio.maternity.client.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/health")
public class ClientHealthController {

    @GetMapping
    public String health() {
        return "client-api ok";
    }
}

