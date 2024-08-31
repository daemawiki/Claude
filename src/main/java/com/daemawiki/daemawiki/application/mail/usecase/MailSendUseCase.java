package com.daemawiki.daemawiki.application.mail.usecase;

import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import reactor.core.publisher.Mono;

public interface MailSendUseCase {
    Mono<Void> send(String to, MailType type);

}