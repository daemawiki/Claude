package com.daemawiki.daemawiki.domain.mail.event.handler;

import com.daemawiki.daemawiki.domain.mail.auth_code.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.global.utils.event.EventFailureHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMailSendEventFailureHandler implements EventFailureHandler<MailSendEvent> {
    @Override
    public void handleFailure(MailSendEvent event, Throwable throwable) {
        authCodeRepository.deleteByEmail(event.to())
                .doOnNext(l -> log.error(l + "- Mail send failed : " + throwable))
                .doOnError(e -> log.error("Redis entity delete failed : " + e))
                .subscribe();
    }

    private final AuthCodeRepository authCodeRepository;
}
