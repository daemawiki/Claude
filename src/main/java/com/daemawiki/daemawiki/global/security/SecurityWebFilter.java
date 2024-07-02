package com.daemawiki.daemawiki.global.security;

import com.daemawiki.daemawiki.global.error.ErrorResponse;
import com.daemawiki.daemawiki.global.security.token.Tokenizer;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SecurityWebFilter implements WebFilter {
    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var token = extractToken(exchange);
        return token.isBlank() ? chain.filter(exchange) : handleAuthentication(exchange, chain, token);
    }

    private Mono<Void> handleAuthentication(ServerWebExchange exchange, WebFilterChain chain, String token) {
        return tokenizer.getAuthentication(token)
                .flatMap(auth -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                .onErrorResume(JwtException.class, e -> handleJwtException(exchange, e));
    }

    private String extractToken(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return tokenizer.extractToken(authorization);
    }

    private Mono<Void> handleJwtException(ServerWebExchange exchange, JwtException e) {
        var errorResponse = ErrorResponse.ofSecurityError(
                HttpStatus.UNAUTHORIZED,
                HANDLE_MESSAGE,
                HANDLE_VIEW_MESSAGE,
                exchange,
                e
        );

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var responseBytes = errorResponse.toString().getBytes();
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(responseBytes))
                );
    }

    private static final String HANDLE_VIEW_MESSAGE = "Invalid or expired JWT.";
    private static final String HANDLE_MESSAGE = "유효하지 않은 토큰입니다.";

    private final Tokenizer tokenizer;
}
