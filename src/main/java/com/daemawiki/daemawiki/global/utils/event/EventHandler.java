package com.daemawiki.daemawiki.global.utils.event;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;

public interface EventHandler<T> {
    void handle(T event);

}
