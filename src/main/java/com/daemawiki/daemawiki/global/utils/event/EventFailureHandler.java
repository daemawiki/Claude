package com.daemawiki.daemawiki.global.utils.event;

public interface EventFailureHandler<T> {
    void handleFailure(T event, Throwable throwable);
}