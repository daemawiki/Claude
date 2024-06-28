package com.daemawiki.daemawiki.domain.mail.service;

import com.daemawiki.daemawiki.domain.mail.auth_code.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.mail.auth_user.repository.AuthUserRepository;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailVerifyUseCase;
import com.daemawiki.daemawiki.global.error.customs.WrongRedisConnectionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserMailVerifyService implements UserMailVerifyUseCase {
    @Override
    public Mono<Void> verify(String target, String code) {
        return authCodeRepository.findByMail(target)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 인증 코드 존재 x
                .filter(model -> model.code().equals(code))
                .switchIfEmpty(Mono.error(new RuntimeException())) // 메일 인증 실패
                .flatMap(model -> authUserRepository.save(model.email()))
                .then()
                .onErrorMap(e -> e);
    }

    private final AuthCodeRepository authCodeRepository;
    private final AuthUserRepository authUserRepository;
}
