package com.daemawiki.daemawiki.common.error;

import com.daemawiki.daemawiki.common.error.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ErrorResponse(
        int status,
        String message,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime timestamp,
        String errorId
) {
    public ErrorResponse {
        final var httpStatus = HttpStatus.valueOf(status());

        if (httpStatus.is5xxServerError()) {
            message = httpStatus.name();
        }

        errorId = UUID.randomUUID().toString().substring(0, 7);
    }

    public static ErrorResponse ofCustomException(CustomException e) {
        return new ErrorResponse(
                e.getStatus().value(),
                e.getMessage(),
                LocalDateTime.now(),
                null
        );
    }

    public static ErrorResponse ofSecurityError(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now(),
                null
        );
    }
}
