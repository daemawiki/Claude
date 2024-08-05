package com.daemawiki.daemawiki.common.security.session.component.service;

import com.daemawiki.daemawiki.common.security.session.model.SessionEntity;
import reactor.core.publisher.Mono;

public interface SaveSessionComponent {
    Mono<SessionEntity> save(SessionEntity entity);
}
