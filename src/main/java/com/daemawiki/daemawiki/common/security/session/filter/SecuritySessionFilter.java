package com.daemawiki.daemawiki.common.security.session.filter;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import com.daemawiki.daemawiki.common.security.session.component.handler.SessionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SecuritySessionFilter implements WebFilter {

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
                .flatMap(auth -> chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
                .onErrorResume(e -> handleSessionException(exchange, e));
    }

    private HttpCookie getSessionId(ServerWebExchange exchange) {
        return exchange.getRequest()
                .getCookies()
                .getFirst("sessionId");
    }

    private Mono<Void> handleSessionException(ServerWebExchange exchange, Throwable e) {
        var errorResponse = ErrorResponse.ofSecurityError(
                HttpStatus.UNAUTHORIZED,
                HANDLE_MESSAGE
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

    private static Mono<? extends DataBuffer> wrapResponseToDataBuffer(ServerWebExchange exchange, byte[] responseBytes) {
        return Mono.just(exchange.getResponse()
                .bufferFactory()
                .wrap(responseBytes));
    }

    private static final String HANDLE_VIEW_MESSAGE = "Invalid or expired session.";
    private static final String HANDLE_MESSAGE = "유효하지 않은 세션입니다.";

    private final ObjectMapper objectMapper;
    private final SessionHandler sessionHandler;
}
