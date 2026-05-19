package com.portfolio.maternity.client.cart;

import com.portfolio.maternity.global.security.AuthPrincipal;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client-api/v1/cart")
public class ClientCartController {

    private final ClientCartService clientCartService;

    public ClientCartController(ClientCartService clientCartService) {
        this.clientCartService = clientCartService;
    }

    @GetMapping
    public List<CartItemResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientCartService.findMine(principal.id());
    }

    @PostMapping
    public CartItemResponse addOrUpdate(
            Authentication authentication,
            @Valid @RequestBody CartItemRequest request
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientCartService.addOrUpdate(principal.id(), request);
    }
}

