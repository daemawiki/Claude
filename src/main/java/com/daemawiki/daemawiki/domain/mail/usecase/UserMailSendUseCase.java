package com.daemawiki.daemawiki.domain.mail.usecase;

import com.daemawiki.daemawiki.domain.mail.dto.AuthCodeRequest;
import reactor.core.publisher.Mono;

public interface UserMailSendUseCase {
    Mono<Void> send(AuthCodeRequest request);

}