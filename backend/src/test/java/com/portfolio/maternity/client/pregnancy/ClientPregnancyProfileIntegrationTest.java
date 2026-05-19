package com.portfolio.maternity.client.pregnancy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.portfolio.maternity.support.MockMvcAuthSupport;
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
        String token = MockMvcAuthSupport.signupMemberAndExtractToken(mockMvc, "profile@example.com");

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

}
