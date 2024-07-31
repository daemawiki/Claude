package com.daemawiki.daemawiki.global.security;

import com.daemawiki.daemawiki.global.security.session.filter.SecuritySessionFilter;
import com.daemawiki.daemawiki.global.security.session.component.handler.SessionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchange -> authorizeExchange
                        .pathMatchers("/api/auth/**", "/api/mail/**").permitAll()
                        .anyExchange().authenticated())
                .addFilterBefore(new SecuritySessionFilter(objectMapper, sessionHandler), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    private final SessionHandler sessionHandler;
    private final ObjectMapper objectMapper;
}
