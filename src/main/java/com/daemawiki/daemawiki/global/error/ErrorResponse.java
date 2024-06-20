package com.daemawiki.daemawiki.global.error;

import com.daemawiki.daemawiki.global.error.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp,
        String path,
        String exception
) {
    private static ErrorResponse of(Error error, String path, String exception) {
        return new ErrorResponse(error.getStatus(), error.getMessage(), LocalDateTime.now(), path, exception);
    }

    public static ResponseEntity<ErrorResponse> of(CustomException e, ServerRequest request) {
        var error = e.getError();
        return ResponseEntity.status(error.getStatus())
                .body(of(
                        error,
                        request.path(),
                        e.getClass().getSimpleName()
                ));
    }
}
