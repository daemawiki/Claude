package com.daemawiki.daemawiki.common.error;

import com.daemawiki.daemawiki.common.error.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime timestamp
) {
    public static ErrorResponse ofCustomException(CustomException e) {
        return new ErrorResponse(
                        e.getStatus().value(),
                        e.getMessage(),
                        LocalDateTime.now()
                );
    }

    public static ErrorResponse ofSecurityError(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
    }
}
