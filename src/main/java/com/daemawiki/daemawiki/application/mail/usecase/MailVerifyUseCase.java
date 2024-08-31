package com.daemawiki.daemawiki.application.mail.usecase;

import reactor.core.publisher.Mono;

public interface MailVerifyUseCase {
    Mono<Void> verify(String target, String code);

}
