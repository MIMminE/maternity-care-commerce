package com.portfolio.maternity.client.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.portfolio.maternity.domain.product.Product;
import com.portfolio.maternity.domain.product.ProductRepository;
import com.portfolio.maternity.domain.product.ProductStatus;
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
class ClientCommerceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void memberCanAddProductToCartAndCreateOrder() throws Exception {
        Product product = productRepository.save(new Product(
                "산모애 바디로션",
                "BODY_CARE",
                BigDecimal.valueOf(32000),
                10,
                ProductStatus.ON_SALE
        ));
        String token = signupAndExtractToken("commerce@example.com");

        mockMvc.perform(get("/client-api/v1/products")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("산모애 바디로션"));

        mockMvc.perform(post("/client-api/v1/cart")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": %d,
                                  "quantity": 2
                                }
                                """.formatted(product.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.lineAmount").value(64000.0));

        mockMvc.perform(post("/client-api/v1/orders")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.totalAmount").value(64000.0))
                .andExpect(jsonPath("$.items[0].productName").value("산모애 바디로션"));

        mockMvc.perform(get("/client-api/v1/cart")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    private String signupAndExtractToken(String email) throws Exception {
        String response = mockMvc.perform(post("/client-api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "password123!",
                                  "name": "테스트 산모",
                                  "phoneNumber": "010-0000-0000",
                                  "termsAgreed": true,
                                  "privacyAgreed": true,
                                  "sensitiveInformationAgreed": true,
                                  "marketingAgreed": true
                                }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return response.split("\"accessToken\":\"")[1].split("\"")[0];
    }
}

