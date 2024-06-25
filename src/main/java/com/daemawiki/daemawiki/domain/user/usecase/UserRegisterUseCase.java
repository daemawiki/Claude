package com.daemawiki.daemawiki.domain.user.usecase;

import com.daemawiki.daemawiki.domain.user.dto.UserRegisterRequest;
import reactor.core.publisher.Mono;

public interface UserRegisterUseCase {
    Mono<Void> register(UserRegisterRequest request);

}
