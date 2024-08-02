package com.daemawiki.daemawiki.domain.mail.service;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.auth_code.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.mail.model.MailType;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.global.mail.MailSenderProperties;
import com.daemawiki.daemawiki.global.utils.AuthCodeGenerator;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "유저 메일 전송 서비스")
public class UserMailSendService implements UserMailSendUseCase {
    @Override
    public Mono<Void> send(String to, MailType type) {
        return userRepository.findByEmail(to)
                .flatMap(user -> MailType.REGISTER.equals(type)
                        ? Mono.error(new RuntimeException()) // 이메일이 이미 사용 중일 때
                        : Mono.empty())
                .then(Mono.defer(() -> processSendMail(to)));
    }

    private Mono<Void> processSendMail(String to) {
        return saveAuthCode(AuthCodeModel.of(to, authCodeGenerator.generate(CODE_LENGTH)))
                .doOnNext(authCodeModel -> log.info("authCode: {} to: {}", authCodeModel.code(), authCodeModel.email()))
                .flatMap(authCodeModel -> {
                    sendMailInBackground(authCodeModel);
                    return Mono.empty();
                });
    }

    private void sendMailInBackground(AuthCodeModel authCodeModel) {
        Mono.fromRunnable(() -> sendMail(authCodeModel))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    private void sendMail(AuthCodeModel authCodeModel) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(authCodeModel.email());
            helper.setSubject(MAIL_TITLE);
            helper.setText(String.format(MAIL_TEMPLATE, authCodeModel.code()), true);
            helper.setFrom(new InternetAddress(mailSenderProperties.email(), MAIL_TITLE));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private Mono<AuthCodeModel> saveAuthCode(AuthCodeModel authCodeModel) {
        return authCodeRepository.save(authCodeModel)
                .thenReturn(authCodeModel)
                .onErrorMap(e -> e);
    }

    private static final int CODE_LENGTH = 6;
    private static final String MAIL_TITLE = "DSM 메일 인증";
    private static final String MAIL_TEMPLATE =
            "<div style='margin: 10px; background-color: #f5f5f5; padding: 20px; border-radius: 10px;'>"
                    + "<p style='font-size: 16px; color: #333;'><b><span style='color: #007bff;'>D</span><span style='color: #ffcc00;'>S</span><span style='color: #ff0000;'>M</span></b> 이메일 인증 코드 :</p>"
                    + "<p style='font-size: 24px; font-weight: bold; color: #007bff; letter-spacing: 3px;'>%s</p>"
                    + "<p style='font-size: 14;font-style: italic; color: #999;'>인증 코드는 30분 동안 유효합니다.</p>"
                    + "</div>";

    private final MailSenderProperties mailSenderProperties;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeGenerator authCodeGenerator;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
}