package com.daemawiki.daemawiki.global.security.token;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface Tokenizer {
    Mono<String> createToken(String user);
    Mono<String> reissue(String token);
    String removePrefix(String bearerToken);
    Mono<Authentication> getAuthentication(String token);
}
