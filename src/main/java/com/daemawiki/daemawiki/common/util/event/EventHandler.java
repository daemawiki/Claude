package com.daemawiki.daemawiki.common.util.event;

public interface EventHandler<T> {
    void handle(T event);
}
