package com.daemawiki.daemawiki.domain.user.component.impl;

import com.daemawiki.daemawiki.domain.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class CurrentUserImpl implements CurrentUser {
    @Override
    public Mono<UserEntity> get() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Principal::getName)
                .flatMap(userRepository::findByEmail);
    }

    private final UserRepository userRepository;
}
