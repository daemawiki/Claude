package com.daemawiki.daemawiki.common.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    private final String message;

    private final Throwable cause;

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public CustomException(
            final HttpStatus status,
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
        this.status = status;
        this.message = message;
        this.cause = cause;
    }

    public CustomException(
            final HttpStatus status,
            final String message
    ) {
        this(status, message, null);
    }
}

