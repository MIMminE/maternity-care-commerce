package com.portfolio.maternity.global.config;

import com.portfolio.maternity.global.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/client-api/v1/health",
                                "/client-api/v1/auth/signup",
                                "/client-api/v1/auth/login",
                                "/admin-api/v1/health",
                                "/admin-api/v1/auth/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/client-api/**").hasRole("MEMBER")
                        .requestMatchers(HttpMethod.GET, "/admin-api/v1/audit-logs/**").hasAnyRole("ADMIN", "LEGAL_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/admin-api/v1/marketing/**").hasAnyRole("ADMIN", "MARKETER", "LEGAL_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/admin-api/v1/members/**").hasAnyRole("ADMIN", "CS_MANAGER", "LEGAL_MANAGER")
                        .requestMatchers("/admin-api/v1/consultations/**").hasAnyRole("ADMIN", "CS_MANAGER")
                        .requestMatchers("/admin-api/v1/inquiries/**").hasAnyRole("ADMIN", "CS_MANAGER")
                        .requestMatchers("/admin-api/v1/products/**").hasAnyRole("ADMIN", "OPERATION_MANAGER")
                        .requestMatchers("/admin-api/v1/statistics/**").hasAnyRole("ADMIN", "OPERATION_MANAGER")
                        .requestMatchers("/admin-api/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
