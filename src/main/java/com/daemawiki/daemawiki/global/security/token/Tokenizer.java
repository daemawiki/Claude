package com.daemawiki.daemawiki.global.security.token;

import reactor.core.publisher.Mono;

public interface Tokenizer {
    Mono<String> createToken(String user);
    Mono<String> reissue(String token);
}
