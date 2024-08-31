package com.daemawiki.daemawiki.common.util.event;

public interface EventFailureHandler<T> {
    void handleFailure(T event);
}