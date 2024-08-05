package com.daemawiki.daemawiki.domain.mail.repository;

import com.daemawiki.daemawiki.domain.mail.model.AuthCodeModel;
import reactor.core.publisher.Mono;

public interface AuthCodeRepository {
    Mono<Boolean> save(AuthCodeModel model);
    Mono<AuthCodeModel> findByMail(String email);
    Mono<Void> delete(AuthCodeModel model);
    Mono<Long> deleteByEmail(String email);

}
