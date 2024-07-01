package com.daemawiki.daemawiki.global.security;

import com.daemawiki.daemawiki.global.security.token.Tokenizer;
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
                        .pathMatchers("/api/auth/**").permitAll()
                        .anyExchange().authenticated())
                .addFilterBefore(new SecurityWebFilter(tokenizer), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

    private final Tokenizer tokenizer;
}
