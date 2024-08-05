package com.daemawiki.daemawiki.common.error.exception;

import com.daemawiki.daemawiki.common.error.Error;

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
