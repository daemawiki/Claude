package com.daemawiki.daemawiki.security.session.filter;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import com.daemawiki.daemawiki.security.session.component.handler.SessionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RequiredArgsConstructor
public class SecuritySessionFilter implements WebFilter {
    private static final String HANDLE_VIEW_MESSAGE = "Invalid or expired session.";
    private static final String HANDLE_MESSAGE = "유효하지 않은 세션입니다.";

    private final ObjectMapper objectMapper;
    private final SessionHandler sessionHandler;

    @NonNull
    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var sessionCookie = getSessionId(exchange);

        return (sessionCookie == null)
                ? chain.filter(exchange)
                : handleAuthentication(exchange, chain, sessionCookie.getValue());
    }

    private Mono<Void> handleAuthentication(ServerWebExchange exchange, WebFilterChain chain, String sessionId) {
        return sessionHandler.getAuthentication(sessionId, exchange.getRequest().getRemoteAddress())
                .doOnSuccess(ignored -> refreshSessionCookie(exchange, sessionId))
                .flatMap(auth -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                .onErrorResume(e -> handleSessionException(exchange, e));
    }

    private HttpCookie getSessionId(ServerWebExchange exchange) {
        return exchange.getRequest()
                .getCookies()
                .getFirst("sessionId");
    }

    private void refreshSessionCookie(ServerWebExchange exchange, String sessionId) {
        exchange.getResponse().addCookie(ResponseCookie.from("sessionId", sessionId)
                .path("/api")
                .httpOnly(true)
                .secure(true)
                .maxAge(Duration.ofHours(3))
                .build());
    }

    private Mono<Void> handleSessionException(ServerWebExchange exchange, Throwable e) {
        var errorResponse = ErrorResponse.ofSecurityError(
                HttpStatus.UNAUTHORIZED,
                HANDLE_MESSAGE,
                HANDLE_VIEW_MESSAGE,
                exchange,
                e
        );
        byte[] responseBytes;

        try {
            responseBytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (JsonProcessingException jsonProcessingException) {
            responseBytes = errorResponse.toString().getBytes();
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        return exchange.getResponse().writeWith(wrapResponseToDataBuffer(exchange, responseBytes));
    }

    private static Mono<DataBuffer> wrapResponseToDataBuffer(ServerWebExchange exchange, byte[] responseBytes) {
        return Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(responseBytes));
    }
}
