package com.daemawiki.daemawiki.domain.mail.auth_user.repository;

import com.daemawiki.daemawiki.domain.mail.auth_user.model.AuthUserModel;
import reactor.core.publisher.Mono;

public interface AuthUserRepository {
    Mono<Boolean> save(String mail);
    Mono<Boolean> findByMail(String mail);
    Mono<Void> delete(String mail);

}
