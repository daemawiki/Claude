package com.daemawiki.daemawiki.domain.mail.usecase;

import com.daemawiki.daemawiki.domain.mail.model.MailType;
import reactor.core.publisher.Mono;

public interface UserMailSendUseCase {
    Mono<Void> send(String to, MailType type);

}