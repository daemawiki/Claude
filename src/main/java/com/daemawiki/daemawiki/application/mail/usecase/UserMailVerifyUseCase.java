package com.daemawiki.daemawiki.application.mail.usecase;

import reactor.core.publisher.Mono;

public interface UserMailVerifyUseCase {
    Mono<Void> verify(String target, String code);

}
