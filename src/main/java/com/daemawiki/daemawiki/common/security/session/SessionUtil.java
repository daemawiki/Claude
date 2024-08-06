package com.daemawiki.daemawiki.common.security.session;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface SessionUtil {
    Mono<Authentication> getAuthentication(String subject);
}
