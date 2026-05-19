package com.portfolio.maternity.client.pregnancy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class ClientPregnancyProfileIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void memberCanUpsertPregnancyProfileAndReadConsents() throws Exception {
        String token = signupAndExtractToken("profile@example.com");

        mockMvc.perform(put("/client-api/v1/pregnancy-profile/me")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": "PREGNANT",
                                  "expectedBirthDate": "2026-12-01",
                                  "pregnancyWeek": 13
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PREGNANT"))
                .andExpect(jsonPath("$.pregnancyWeek").value(13));

        mockMvc.perform(get("/client-api/v1/pregnancy-profile/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expectedBirthDate").value("2026-12-01"));

        mockMvc.perform(get("/client-api/v1/consents/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].consentType").exists());
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

