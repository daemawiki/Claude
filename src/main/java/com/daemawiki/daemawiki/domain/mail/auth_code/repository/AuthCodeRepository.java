package com.daemawiki.daemawiki.domain.mail.auth_code.repository;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import reactor.core.publisher.Mono;

public interface AuthCodeRepository {
    Mono<Boolean> save(AuthCodeModel model);
    Mono<AuthCodeModel> findByMail(String email);
    Mono<Void> delete(AuthCodeModel model);

}
