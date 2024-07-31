//package com.daemawiki.daemawiki.global.security;
//
//import com.daemawiki.daemawiki.global.error.ErrorResponse;
//import com.daemawiki.daemawiki.global.security.token.TokenUtils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.paseto.jpaseto.PasetoException;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@RequiredArgsConstructor
//public class SecurityWebFilter implements WebFilter {
//
//    @NonNull
//    @Override
//    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
//        var token = extractToken(exchange);
//
//        return (token == null) ?
//                chain.filter(exchange) :
//                handleAuthentication(exchange, chain, token);
//    }
//
//    private Mono<Void> handleAuthentication(ServerWebExchange exchange, WebFilterChain chain, String token) {
//        return tokenUtils.getAuthentication(token)
//                .flatMap(auth -> chain.filter(exchange)
//                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)))
//                .onErrorResume(PasetoException.class, e -> handlePasetoException(exchange, e));
//    }
//
//    private String extractToken(ServerWebExchange exchange) {
//        var authorization = exchange.getRequest()
//                .getHeaders()
//                .getFirst(HttpHeaders.AUTHORIZATION);
//
//        return tokenUtils.removePrefix(authorization);
//    }
//
//    private Mono<Void> handlePasetoException(ServerWebExchange exchange, Exception e) {
//        var errorResponse = ErrorResponse.ofSecurityError(
//                HttpStatus.UNAUTHORIZED,
//                HANDLE_MESSAGE,
//                HANDLE_VIEW_MESSAGE,
//                exchange,
//                e
//        );
//        byte[] responseBytes;
//
//        try {
//            responseBytes = objectMapper.writeValueAsBytes(errorResponse);
//        } catch (JsonProcessingException jsonProcessingException) {
//            responseBytes = errorResponse.toString().getBytes();
//        }
//
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//
//        return exchange.getResponse().writeWith(wrapResponseToDataBuffer(exchange, responseBytes));
//    }
//
//    private static Mono<? extends DataBuffer> wrapResponseToDataBuffer(ServerWebExchange exchange, byte[] responseBytes) {
//        return Mono.just(exchange.getResponse()
//                .bufferFactory()
//                .wrap(responseBytes));
//    }
//
//    private static final String HANDLE_VIEW_MESSAGE = "Invalid or expired token.";
//    private static final String HANDLE_MESSAGE = "유효하지 않은 토큰입니다.";
//
//    private final ObjectMapper objectMapper;
//    private final TokenUtils tokenUtils;
//}
