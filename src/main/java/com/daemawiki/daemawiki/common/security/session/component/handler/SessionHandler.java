package com.daemawiki.daemawiki.common.security.session.component.handler;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

public interface SessionHandler {
    Mono<Authentication> getAuthentication(String sessionId, InetSocketAddress socketAddress);
}
