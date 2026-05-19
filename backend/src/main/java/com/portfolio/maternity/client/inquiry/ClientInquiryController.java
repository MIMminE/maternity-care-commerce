package com.portfolio.maternity.client.inquiry;

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
@RequestMapping("/client-api/v1/inquiries")
public class ClientInquiryController {

    private final ClientInquiryService clientInquiryService;

    public ClientInquiryController(ClientInquiryService clientInquiryService) {
        this.clientInquiryService = clientInquiryService;
    }

    @GetMapping
    public List<InquiryResponse> findMine(Authentication authentication) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientInquiryService.findMine(principal.id());
    }

    @PostMapping
    public InquiryResponse create(
            Authentication authentication,
            @Valid @RequestBody InquiryCreateRequest request
    ) {
        AuthPrincipal principal = (AuthPrincipal) authentication.getPrincipal();
        return clientInquiryService.create(principal.id(), request);
    }
}

