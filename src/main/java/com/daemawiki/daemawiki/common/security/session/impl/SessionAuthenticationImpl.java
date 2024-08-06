package com.daemawiki.daemawiki.common.security.session.impl;

import com.daemawiki.daemawiki.common.security.session.SessionUtil;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
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
public class SessionAuthenticationImpl implements SessionUtil {

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

    private final UserRepository userRepository;
}