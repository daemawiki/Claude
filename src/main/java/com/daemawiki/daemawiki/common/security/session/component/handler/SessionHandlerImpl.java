package com.daemawiki.daemawiki.common.security.session.component.handler;

import com.daemawiki.daemawiki.common.security.session.repository.SessionRepository;
import com.daemawiki.daemawiki.common.security.token.TokenUtils;
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
        if (socketAddress == null) {
            return Mono.error(new RuntimeException("잘못된 ip 접근ㅋ"));
        }

        return sessionRepository.findBySessionIdAndIp(sessionId, socketAddress.toString())
                .switchIfEmpty(Mono.error(new RuntimeException("너 누구야!!")))
                .flatMap(session -> tokenUtils.getAuthentication(session.token()));
    }

    private final SessionRepository sessionRepository;
    private final TokenUtils tokenUtils;
}
