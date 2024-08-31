package com.daemawiki.daemawiki.application.mail.event.handler;

import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.common.util.event.EventHandler;
import com.daemawiki.daemawiki.infrastructure.mail.MailSenderProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
class UserMailSendEventHandler implements EventHandler<MailSendEvent> {
    private static final String MAIL_TITLE = "DSM 메일 인증";

    private final MailSenderProperties mailSenderProperties;
    private final JavaMailSender mailSender;

    @Override
    @TransactionalEventListener(MailSendEvent.class)
    public void handle(MailSendEvent event) {
        Mono.fromCallable(() -> sendMail(event))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private boolean sendMail(MailSendEvent event) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(event.to());
        helper.setSubject(MAIL_TITLE);
        helper.setText(event.content(), true);
        helper.setFrom(new InternetAddress(mailSenderProperties.email(), MAIL_TITLE));

        mailSender.send(message);
        return true;
    }
}
