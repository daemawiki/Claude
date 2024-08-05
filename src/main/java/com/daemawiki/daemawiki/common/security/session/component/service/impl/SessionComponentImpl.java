package com.daemawiki.daemawiki.common.security.session.component.service.impl;

import com.daemawiki.daemawiki.common.security.session.component.service.SaveSessionComponent;
import com.daemawiki.daemawiki.common.security.session.model.SessionEntity;
import com.daemawiki.daemawiki.common.security.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SessionComponentImpl implements SaveSessionComponent {

    @Override
    public Mono<SessionEntity> save(SessionEntity entity) {
        return sessionRepository.save(entity);
    }

    private final SessionRepository sessionRepository;
}