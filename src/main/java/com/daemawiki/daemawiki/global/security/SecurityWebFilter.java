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
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        var token = tokenizer.extractToken(authorization);

        if (token != null) {
            return tokenizer.getAuthentication(token)
                    .flatMap(auth -> chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                    .onErrorResume(JwtException.class, e -> handleJwtException(exchange, e));
        }
        return chain.filter(exchange);
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

        return exchange.getResponse()
                .writeWith(
                        Mono.just(exchange.getResponse()
                                .bufferFactory()
                                .wrap(errorResponse.toString().getBytes())
                        )
                );
    }

    private static final String HANDLE_MESSAGE = "유효하지 않은 토큰입니다.";
    private static final String HANDLE_VIEW_MESSAGE = "Invalid or expired JWT.";

    private final Tokenizer tokenizer;
}
