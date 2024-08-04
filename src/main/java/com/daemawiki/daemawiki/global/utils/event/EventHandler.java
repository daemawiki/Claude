package com.daemawiki.daemawiki.global.utils.event;

public interface EventHandler<T> {
    void handle(T event);
}
