package com.portfolio.maternity.admin.statistics;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.portfolio.maternity.domain.adminuser.AdminRole;
import com.portfolio.maternity.domain.adminuser.AdminUser;
import com.portfolio.maternity.domain.adminuser.AdminUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminOperationsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void adminCanReadDashboardAndMarketingMembers() throws Exception {
        adminUserRepository.save(new AdminUser(
                "admin@example.com",
                "운영 관리자",
                passwordEncoder.encode("password123!"),
                AdminRole.ADMIN
        ));
        signup("marketing-ok@example.com", true);
        signup("marketing-no@example.com", false);
        String adminToken = loginAdminAndExtractToken();

        mockMvc.perform(get("/admin-api/v1/statistics/dashboard")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalMembers").value(2))
                .andExpect(jsonPath("$.marketingAgreedMembers").value(1));

        mockMvc.perform(get("/admin-api/v1/marketing/members")
                        .header("Authorization", "Bearer " + adminToken)
                        .header("X-Audit-Reason", "마케팅 캠페인 대상 확인"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("marketing-ok@example.com"));
    }

    private void signup(String email, boolean marketingAgreed) throws Exception {
        mockMvc.perform(post("/client-api/v1/auth/signup")
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
                                  "marketingAgreed": %s
                                }
                                """.formatted(email, marketingAgreed)))
                .andExpect(status().isOk());
    }

    private String loginAdminAndExtractToken() throws Exception {
        String response = mockMvc.perform(post("/admin-api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "admin@example.com",
                                  "password": "password123!"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return response.split("\"accessToken\":\"")[1].split("\"")[0];
    }
}

