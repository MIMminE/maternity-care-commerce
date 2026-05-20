package com.portfolio.maternity.support;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public final class MockMvcAuthSupport {

    private MockMvcAuthSupport() {
    }

    public static String signupMemberAndExtractToken(MockMvc mockMvc, String email) throws Exception {
        return signupMemberAndExtractToken(mockMvc, email, true);
    }

    public static String signupMemberAndExtractToken(
            MockMvc mockMvc,
            String email,
            boolean marketingAgreed
    ) throws Exception {
        String response = mockMvc.perform(post("/client-api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "password123!",
                                  "name": "테스트 사용자",
                                  "phoneNumber": "010-0000-0000",
                                  "termsAgreed": true,
                                  "privacyAgreed": true,
                                  "sensitiveInformationAgreed": true,
                                  "marketingAgreed": %s
                                }
                                """.formatted(email, marketingAgreed)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return extractAccessToken(response);
    }

    public static String loginAdminAndExtractToken(MockMvc mockMvc, String email) throws Exception {
        String response = mockMvc.perform(post("/admin-api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "password": "password123!"
                                }
                                """.formatted(email)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return extractAccessToken(response);
    }

    private static String extractAccessToken(String response) {
        return response.split("\"accessToken\":\"")[1].split("\"")[0];
    }
}

