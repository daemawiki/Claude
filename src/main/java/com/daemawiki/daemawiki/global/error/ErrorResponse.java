package com.daemawiki.daemawiki.global.error;

import com.daemawiki.daemawiki.global.error.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        String viewMessage,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime timestamp,
        String path,
        String exception,
        String detail,
        String cause
) {
    public static ResponseEntity<ErrorResponse> ofCustomException(CustomException e, ServerHttpRequest request) {
        var error = e.getError();
        return ResponseEntity.status(error.getStatus())
                .body(new ErrorResponse(
                        error.getStatus(),
                        error.getMessage(),
                        error.getViewMessage(),
                        LocalDateTime.now(),
                        request.getPath().toString(),
                        e.getClass().getSimpleName(),
                        error.getDetail(),
                        e.getCause() != null ? e.getCause().toString() : "Not yet."
                ));
    }

    public static ErrorResponse ofSecurityError(HttpStatus status, String message, String viewMessage, ServerWebExchange exchange, Throwable e) {
        e = e == null ? new RuntimeException() : e;

        return new ErrorResponse(
                status.value(),
                message,
                viewMessage,
                LocalDateTime.now(),
                exchange.getRequest().getPath().toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                e.getCause() != null? e.getCause().toString() : "Not yet."
        );
    }
}
