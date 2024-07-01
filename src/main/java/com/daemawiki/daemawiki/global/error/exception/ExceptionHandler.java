package com.daemawiki.daemawiki.global.error.exception;

import com.daemawiki.daemawiki.global.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleCustomException(CustomException e, ServerHttpRequest request) {
        return Mono.just(
                ErrorResponse.of(e, request)
        );
    }
}
