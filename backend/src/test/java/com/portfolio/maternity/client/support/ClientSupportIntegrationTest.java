package com.portfolio.maternity.client.support;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductRepository;
import com.portfolio.maternity.domain.product.ProductStatus;
import com.portfolio.maternity.support.MockMvcAuthSupport;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClientSupportIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void memberCanCreateConsultationAndInquiry() throws Exception {
        Product product = productRepository.save(new Product(
                "케어 샴푸",
                "HAIR_CARE",
                BigDecimal.valueOf(28000),
                5,
                ProductStatus.ON_SALE
        ));
        String token = MockMvcAuthSupport.signupMemberAndExtractToken(mockMvc, "support@example.com");

        mockMvc.perform(post("/client-api/v1/consultations")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "출산 후 사용 문의",
                                  "body": "출산 직후에도 사용할 수 있나요?"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REQUESTED"));

        mockMvc.perform(get("/client-api/v1/consultations")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("출산 후 사용 문의"));

        mockMvc.perform(post("/client-api/v1/inquiries")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": %d,
                                  "title": "향 문의",
                                  "body": "향이 강한 편인가요?"
                                }
                                """.formatted(product.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("RECEIVED"))
                .andExpect(jsonPath("$.productName").value("케어 샴푸"));
    }

}
