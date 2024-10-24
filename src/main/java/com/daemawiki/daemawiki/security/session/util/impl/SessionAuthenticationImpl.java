package com.daemawiki.daemawiki.security.session.util.impl;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.security.session.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
class SessionAuthenticationImpl implements SessionUtil {
    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> getAuthentication(String subject) {
        return createAuthenticatedUserBySubject(subject);
    }

    private Mono<Authentication> createAuthenticatedUserBySubject(String subject) {
        return userRepository.findByEmail(subject)
                .switchIfEmpty(Mono.error(new RuntimeException("O_M_G")))
                .map(user -> createAuthenticatedUser(subject, user))
                .map(SessionAuthenticationImpl::createUserNamePasswordAuthenticationToken);
    }

    private static UsernamePasswordAuthenticationToken createUserNamePasswordAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
    }

    private static User createAuthenticatedUser(String subject, UserEntity user) {
        return new User(subject, "", List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }
}