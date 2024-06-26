package com.daemawiki.daemawiki.domain.mail.usecase;

import reactor.core.publisher.Mono;

public interface UserMailVerifyUseCase {
    Mono<Void> verify(String target, String code);

}
