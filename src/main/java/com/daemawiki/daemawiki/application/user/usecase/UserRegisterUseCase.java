package com.daemawiki.daemawiki.application.user.usecase;

import com.daemawiki.daemawiki.interfaces.user.dto.UserRegisterRequest;
import reactor.core.publisher.Mono;

public interface UserRegisterUseCase {
    Mono<Void> register(UserRegisterRequest request);

}
