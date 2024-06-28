package com.daemawiki.daemawiki.domain.mail.service;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.auth_code.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.mail.model.MailType;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.global.mail.MailSenderProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "유저 메일 전송 서비스")
public class UserMailSendService implements UserMailSendUseCase {
    @Override
    public Mono<Void> send(String to, String type) {
        return userRepository.findByEmail(to)
                .flatMap(user -> {
                    if (Objects.equals(type.toUpperCase(), MailType.REGISTER.name())) {
                        return Mono.error(new RuntimeException()); // 메일이 이미 사용 중일 때
                    } else {
                        return Mono.empty();
                    }
                })
                .then(Mono.defer(() -> saveAuthCode(AuthCodeModel.of(to, getRandomCode()))
                                .doOnNext(authCodeModel -> log.info("authCode: {} to: {}", authCodeModel.code(), authCodeModel.email()))
                                        .flatMap(authCodeModel -> {
                                            sendMail(authCodeModel)
                                                            .subscribeOn(Schedulers.boundedElastic())
                                                            .subscribe();
                                            return Mono.empty();
                                        })));
    }

    private Mono<Void> sendMail(AuthCodeModel authCodeModel) {
        return Mono.fromRunnable(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                helper.setTo(authCodeModel.email());
                helper.setSubject("DSM 메일 인증");

                String mail = getMailTemplate(authCodeModel.code());

                helper.setText(mail, true);
                helper.setFrom(new InternetAddress(mailSenderProperties.email(), "DSM-MAIL-AUTH"));

                mailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(); // 메일 전송 실패
            }
        }).then();
    }

    private Mono<AuthCodeModel> saveAuthCode(AuthCodeModel authCodeModel) {
        return authCodeRepository.save(authCodeModel)
                .thenReturn(authCodeModel)
                .onErrorMap(e -> e);
    }

    private String getMailTemplate(String key) {
        return "<div style='margin: 10px; background-color: #f5f5f5; padding: 20px; border-radius: 10px;'>"
                + "<p style='font-size: 16px; color: #333;'><b><span style='color: #007bff;'>D</span><span style='color: #ffcc00;'>S</span><span style='color: #ff0000;'>M</span></b> 이메일 인증 코드 :</p>"
                + "<p style='font-size: 24px; font-weight: bold; color: #007bff; letter-spacing: 3px;'>" + key + "</p>"
                + "<p style='font-size: 14;font-style: italic; color: #999;'>인증 코드는 30분 동안 유효합니다.</p>"
                + "</div>";
    }

    private String getRandomCode() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] seed = new byte[32];
        secureRandom.nextBytes(seed);
        secureRandom.setSeed(seed);
        byte[] randomBytes = new byte[4];
        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    private final MailSenderProperties mailSenderProperties;
    private final AuthCodeRepository authCodeRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
}