package com.daemawiki.daemawiki.global.util.event;

public interface EventFailureHandler<T> {
    void handleFailure(T event, Throwable throwable);
}