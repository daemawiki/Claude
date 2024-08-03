package com.daemawiki.daemawiki.domain.mail.event.handler;

import com.daemawiki.daemawiki.domain.mail.event.model.MailSendEvent;

public interface UserMailSendEventHandler {
    void handleEvent(MailSendEvent event);
}
