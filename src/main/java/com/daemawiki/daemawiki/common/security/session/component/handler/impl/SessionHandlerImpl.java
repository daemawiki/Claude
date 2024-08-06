package com.daemawiki.daemawiki.common.security.session.component.handler.impl;

import com.daemawiki.daemawiki.common.security.session.component.handler.SessionHandler;
import com.daemawiki.daemawiki.common.security.session.repository.SessionRepository;
import com.daemawiki.daemawiki.common.security.session.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

@Component
@RequiredArgsConstructor
public class SessionHandlerImpl implements SessionHandler {

    @Override
    public Mono<Authentication> getAuthentication(String sessionId, InetSocketAddress socketAddress) {
        return Mono.justOrEmpty(socketAddress)
                .switchIfEmpty(Mono.error(new RuntimeException("잘못된 접근 ㅋㅋ")))
                .flatMap(address -> sessionRepository.findBySessionIdAndIp(sessionId, address.toString()))
                .switchIfEmpty(Mono.error(new RuntimeException("너 누구야!!")))
                .flatMap(sessionRepository::save)
                .flatMap(session -> sessionUtil.getAuthentication(session.userName()));
    }

    private final SessionRepository sessionRepository;
    private final SessionUtil sessionUtil;
}
