package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.domain.user.dto.UserLoginResponse;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.domain.user.usecase.UserLoginUseCase;
import com.daemawiki.daemawiki.global.security.token.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class UserLoginService implements UserLoginUseCase {
    @Override
    public Mono<UserLoginResponse> login(UserLoginRequest request) {
        return userRepository.findByEmail(request.email())
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .flatMap(user -> validatePasswordAndCreateToken(user, request.password()))
                .map(this::createLoginResponse);
    }

    private Mono<Tuple2<UserEntity, String>> validatePasswordAndCreateToken(UserEntity user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Mono.error(new RuntimeException());
        }
        return tokenizer.createToken(user.getEmail())
                .map(token -> Tuples.of(user, token));
    }

    private UserLoginResponse createLoginResponse(Tuple2<UserEntity, String> userAndToken) {
        var user = userAndToken.getT1();
        var token = userAndToken.getT2();

        return UserLoginResponse.of(
                token,
                user.getName(),
                user.getRole()
        );
    }

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Tokenizer tokenizer;
}
