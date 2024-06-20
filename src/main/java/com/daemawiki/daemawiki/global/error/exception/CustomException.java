package com.daemawiki.daemawiki.global.error.exception;

import com.daemawiki.daemawiki.global.error.Error;

public class CustomException extends RuntimeException {
    private final Error error;

    public Error getError() {
        return error;
    }

    protected CustomException(Error error) {
        this.error = error;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
