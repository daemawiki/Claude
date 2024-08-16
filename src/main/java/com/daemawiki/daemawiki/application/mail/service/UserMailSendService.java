package com.daemawiki.daemawiki.application.mail.service;

import com.daemawiki.daemawiki.domain.mail.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.application.mail.event.model.MailSendEvent;
import com.daemawiki.daemawiki.domain.mail.model.type.MailType;
import com.daemawiki.daemawiki.application.mail.usecase.UserMailSendUseCase;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.common.util.random.AuthCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "유저 메일 전송 서비스")
class UserMailSendService implements UserMailSendUseCase {
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
                .map(authCodeModel -> new MailSendEvent(authCodeModel.email(), String.format(MAIL_TEMPLATE, authCodeModel.code())))
                .doOnSuccess(eventPublisher::publishEvent)
                .then();
    }

    private Mono<AuthCodeModel> saveAuthCode(AuthCodeModel authCodeModel) {
        return authCodeRepository.save(authCodeModel)
                .thenReturn(authCodeModel)
                .onErrorMap(e -> e);
    }

    private static final int CODE_LENGTH = 6;
    private static final String MAIL_TEMPLATE =
            "<div style='margin: 10px; background-color: #f5f5f5; padding: 20px; border-radius: 10px;'>"
                    + "<p style='font-size: 16px; color: #333;'><b><span style='color: #007bff;'>D</span><span style='color: #ffcc00;'>S</span><span style='color: #ff0000;'>M</span></b> 이메일 인증 코드 :</p>"
                    + "<p style='font-size: 24px; font-weight: bold; color: #007bff; letter-spacing: 3px;'>%s</p>"
                    + "<p style='font-size: 14;font-style: italic; color: #999;'>인증 코드는 30분 동안 유효합니다.</p>"
                    + "</div>";


    private final ApplicationEventPublisher eventPublisher;
    private final AuthCodeRepository authCodeRepository;
    private final AuthCodeGenerator authCodeGenerator;
    private final UserRepository userRepository;
}