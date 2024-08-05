package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import reactor.core.publisher.Mono;

public interface CreateUserDocumentUseCase {

    Mono<Void> create(UserEntity user);
}
