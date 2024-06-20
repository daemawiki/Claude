package com.daemawiki.daemawiki.global.error;

import lombok.Getter;

@Getter
public enum Error {
    TEST(400, "test");

    private final int status;
    private final String message;

    Error(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
