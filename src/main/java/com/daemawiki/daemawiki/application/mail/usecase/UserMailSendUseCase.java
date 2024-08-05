package com.daemawiki.daemawiki.application.mail.usecase;

import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import reactor.core.publisher.Mono;

public interface UserMailSendUseCase {
    Mono<Void> send(String to, MailType type);

}