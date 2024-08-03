package com.daemawiki.daemawiki.domain.mail.usecase;

import com.daemawiki.daemawiki.domain.mail.model.event.MailSendEvent;

public interface UserMailSendEventHandler {
    void handleEvent(MailSendEvent event);
}
