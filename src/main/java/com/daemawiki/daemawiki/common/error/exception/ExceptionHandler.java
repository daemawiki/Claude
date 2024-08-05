package com.daemawiki.daemawiki.common.error.exception;

import com.daemawiki.daemawiki.common.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleCustomException(CustomException e, ServerHttpRequest request) {
        return Mono.just(
                ErrorResponse.ofCustomException(e, request)
        );
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
//    public Mono<ResponseEntity<ErrorResponse>> handleAllException() {
//        return Mono.just(
//                ErrorResponse.
//        )
//    }
}
