package com.daemawiki.daemawiki.global.security.session.component.service;

import com.daemawiki.daemawiki.global.security.session.model.SessionEntity;
import reactor.core.publisher.Mono;

public interface SaveSessionComponent {
    Mono<SessionEntity> save(SessionEntity entity);
}
