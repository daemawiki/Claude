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
public class UserMailSendEventFailureHandlerImpl implements EventFailureHandler<MailSendEvent> {
    @Override
    public void handleFailure(MailSendEvent event, Throwable throwable) {
        authCodeRepository.deleteByEmail(event.to()).subscribe();

        log.error("메일 전송 실패 : " + throwable);
    }

    private final AuthCodeRepository authCodeRepository;
}
