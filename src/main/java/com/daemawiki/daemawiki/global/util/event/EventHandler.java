package com.daemawiki.daemawiki.global.util.event;

public interface EventHandler<T> {
    void handle(T event);
}
