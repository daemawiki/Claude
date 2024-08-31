package com.daemawiki.daemawiki.application.user.service;

import com.daemawiki.daemawiki.common.security.session.model.SessionModel;
import com.daemawiki.daemawiki.common.security.session.repository.SessionRepository;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.application.user.usecase.UserLoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * 사용자 로그인 서비스 구현 클래스 <br/>
 * 사용자 인증, 토큰 생성 및 로그인 응답 생성 처리
 */
@Service
@RequiredArgsConstructor
class UserLoginService implements UserLoginUseCase {
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Mono<String> login(UserLoginRequest request, ServerHttpRequest serverHttpRequest) {
        return Mono.justOrEmpty(serverHttpRequest.getRemoteAddress())
                .switchIfEmpty(Mono.error(new RuntimeException("너 누구야!!")))
                .flatMap(remoteAddress -> createSession(request, remoteAddress));
    }

    private Mono<String> createSession(UserLoginRequest request, InetSocketAddress remoteAddress) {
        return loginProcess(request)
                .map(user -> SessionModel.of(remoteAddress.toString(), user.getEmail()))
                .flatMap(sessionRepository::save)
                .map(SessionModel::id);
    }

    private Mono<UserEntity> loginProcess(UserLoginRequest request) {
        return userRepository.findByEmail(request.email())
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .flatMap(user -> validatePassword(user, request.password()));
    }

    private Mono<UserEntity> validatePassword(UserEntity user, String requestPassword) {
        return passwordEncoder.matches(requestPassword, user.getPassword())
                ? Mono.just(user)
                : Mono.error(new RuntimeException());
    }
}
