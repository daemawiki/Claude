package com.daemawiki.daemawiki.application.user.service;

import com.daemawiki.daemawiki.common.security.session.component.service.SaveSessionComponent;
import com.daemawiki.daemawiki.common.security.session.model.SessionEntity;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginResponse;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.application.user.usecase.UserLoginUseCase;
import com.daemawiki.daemawiki.common.security.token.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

/**
 * 사용자 로그인 서비스 구현 클래스 <br/>
 * 사용자 인증, 토큰 생성 및 로그인 응답 생성 처리
 */
@Service
@RequiredArgsConstructor
public class UserLoginService implements UserLoginUseCase {

    /**
     * 사용자 로그인 요청 처리 메서드
     *
     * @param request 사용자 로그인 요청 정보를 포함하는 객체
     * @return 로그인 성공 시 UserLoginResponse 객체를 Mono 펑터에 감싸서 반환, 실패 시 Mono error signal
     */
    @Override
    public Mono<UserLoginResponse> login(UserLoginRequest request) {
        return loginProcess(request)
                .map(this::createLoginResponse);
    }

    private Mono<Tuple2<UserEntity, String>> loginProcess(UserLoginRequest request) {
        return userRepository.findByEmail(request.email())
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .flatMap(user -> validatePasswordAndCreateToken(user, request.password()));
    }

    @Override
    public Mono<String> loginSession(UserLoginRequest request, ServerHttpRequest serverHttpRequest) {
        final var remoteAddress = serverHttpRequest.getRemoteAddress();

        if (remoteAddress == null) {
            return Mono.error(new RuntimeException("잘못된 ip 접근"));
        }

        return loginProcess(request)
                .flatMap(tuple -> saveSessionComponent.save(SessionEntity.of(
                        remoteAddress.toString(),
                        tuple.getT2()
                )))
                .map(SessionEntity::id);
    }

    /**
     * 비밀번호 검증 및 토큰 생성
     *
     * @param user DB에서 조회된 사용자 엔티티
     * @param requestPassword 로그인 요청에서 제공된 비밀번호
     * @return 인증 성공 시 사용자 엔티티와 생성된 토큰을 묶은 Tuple2, 비밀번호 불일치 시 Mono error signal
     */
    private Mono<Tuple2<UserEntity, String>> validatePasswordAndCreateToken(UserEntity user, String requestPassword) {
        return passwordEncoder.matches(requestPassword, user.getPassword()) ?
                createTokenProcess(user) :
                Mono.error(new RuntimeException());
    }

    private Mono<Tuple2<UserEntity, String>> createTokenProcess(UserEntity user) {
        return tokenizer.createToken(user.getEmail())
                .map(token -> Tuples.of(user, token));
    }

    /**
     * 로그인 응답 객체 생성
     *
     * @param userAndToken 사용자 엔티티와 토큰을 포함하는 Tuple2
     * @return 생성된 UserLoginResponse 객체 토큰, 유저 이름, 유저 역할 포함.
     */
    private UserLoginResponse createLoginResponse(Tuple2<UserEntity, String> userAndToken) {
        var user = userAndToken.getT1();
        var token = userAndToken.getT2().split("\\.");

        return UserLoginResponse.of(
                token[2],
                user.getName(),
                user.getRole()
        );
    }

    private final SaveSessionComponent saveSessionComponent;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Tokenizer tokenizer;
}
