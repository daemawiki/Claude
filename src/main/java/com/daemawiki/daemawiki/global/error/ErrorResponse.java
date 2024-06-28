package com.daemawiki.daemawiki.global.error;

import com.daemawiki.daemawiki.global.error.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        String viewMessage,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
        LocalDateTime timestamp,
        String path,
        String exception,
        String detail
) {
    private static ErrorResponse of(Error error, String path, String exception) {
        return new ErrorResponse(error.getStatus(), error.getMessage(), error.getViewMessage(), LocalDateTime.now(), path, exception, error.getDetail());
    }

    public static ResponseEntity<ErrorResponse> of(CustomException e) {
        var error = e.getError();
        return ResponseEntity.status(error.getStatus())
                .body(of(
                        error,
                        "path", // TODO: 6/28/24 ServerRequest 대안 필요. 
                        e.getClass().getSimpleName()
                ));
    }
}
