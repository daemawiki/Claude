package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.user.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {
    @Override
    public Mono<Void> register() {
        return null;
    }
}
