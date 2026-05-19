package com.portfolio.maternity.client.order;

import com.portfolio.maternity.global.security.AuthPrincipal;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/orders")
public class ClientOrderController {

    private final ClientOrderService clientOrderService;

    public ClientOrderController(ClientOrderService clientOrderService) {
        this.clientOrderService = clientOrderService;
    }

    @GetMapping
    public List<OrderResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientOrderService.findMine(principal.id());
    }

    @PostMapping
    public OrderResponse create(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientOrderService.createFromCart(principal.id());
    }
}

