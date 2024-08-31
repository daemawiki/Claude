package com.daemawiki.daemawiki.application.mail.event.handler;

import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.common.util.event.EventFailureHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component
@RequiredArgsConstructor
class UserMailSendEventFailureHandler implements EventFailureHandler<MailSendEvent> {
    @Override
    public void handleFailure(MailSendEvent event, Throwable throwable) {
        authCodeRepository.deleteByEmail(event.to())
                .doOnNext(l -> log.error(l + "- Mail send failed : " + throwable))
                .doOnError(e -> log.error("Redis entity delete failed : " + e))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private final AuthCodeRepository authCodeRepository;
}
