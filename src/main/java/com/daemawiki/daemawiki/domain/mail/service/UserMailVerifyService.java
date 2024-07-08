package com.daemawiki.daemawiki.domain.mail.service;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import com.daemawiki.daemawiki.domain.mail.auth_code.repository.AuthCodeRepository;
import com.daemawiki.daemawiki.domain.mail.auth_user.repository.AuthUserRepository;
import com.daemawiki.daemawiki.domain.mail.usecase.UserMailVerifyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserMailVerifyService implements UserMailVerifyUseCase {
    @Override
    public Mono<Void> verify(String target, String code) {
        return authCodeRepository.findByMail(target)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 인증 내역 존재 x
                .filter(model -> isEquals(code, model))
                .switchIfEmpty(Mono.error(new RuntimeException())) // 인증 실패
                .flatMap(this::saveAuthenticationUser);
    }

    private static boolean isEquals(String code, AuthCodeModel model) {
        return model.code().equals(code);
    }

    private Mono<Void> saveAuthenticationUser(AuthCodeModel model) {
        return authUserRepository.save(model.email()).then();
    }

    private final AuthCodeRepository authCodeRepository;
    private final AuthUserRepository authUserRepository;
}
