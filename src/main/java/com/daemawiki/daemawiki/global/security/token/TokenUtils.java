package com.daemawiki.daemawiki.global.security.token;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface TokenUtils {
    String removePrefix(String bearerToken);
    Mono<Authentication> getAuthentication(String token);
}
