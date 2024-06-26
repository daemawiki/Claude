package com.daemawiki.daemawiki.domain.mail.usecase;

import reactor.core.publisher.Mono;

public interface UserMailSendUseCase {
    Mono<Void> send(String to, String type);

}