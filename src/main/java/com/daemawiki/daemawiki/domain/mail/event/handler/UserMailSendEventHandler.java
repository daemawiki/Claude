package com.daemawiki.daemawiki.domain.mail.event.handler;

import com.daemawiki.daemawiki.domain.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.global.mail.MailSenderProperties;
import com.daemawiki.daemawiki.global.utils.event.EventFailureHandler;
import com.daemawiki.daemawiki.global.utils.event.EventHandler;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@RequiredArgsConstructor
public class UserMailSendEventHandler implements EventHandler<MailSendEvent> {

    @Async
    @EventListener
    @Override
    public void handle(MailSendEvent event) {
        Mono.fromRunnable(() -> sendMail(event))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnError(e -> eventEventFailureHandler.handleFailure(event, e))
                .subscribe();
    }

    private void sendMail(MailSendEvent event) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(event.to());
            helper.setSubject(MAIL_TITLE);
            helper.setText(event.content(), true);
            helper.setFrom(new InternetAddress(mailSenderProperties.email(), MAIL_TITLE));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static final String MAIL_TITLE = "DSM 메일 인증";

    private final EventFailureHandler<MailSendEvent> eventEventFailureHandler;
    private final MailSenderProperties mailSenderProperties;
    private final JavaMailSender mailSender;
}
