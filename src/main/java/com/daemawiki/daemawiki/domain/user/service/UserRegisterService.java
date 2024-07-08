package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.document.usecase.CreateUserDocumentUseCase;
import com.daemawiki.daemawiki.domain.mail.auth_user.repository.AuthUserRepository;
import com.daemawiki.daemawiki.domain.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.domain.user.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {
    @Override
    public Mono<Void> register(UserRegisterRequest request) {
        return Mono.when(
                validateEmailExists(request.email()),
                validateEmailAuthentication(request.email())
        )
        .then(createUserDocumentUseCase.create(createUserEntity(request)));
    }

    private Mono<Void> validateEmailExists(String email) {
        return Mono.just(email)
                .filterWhen(this::existsUserEmail)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .then();
    }

    private Mono<Boolean> existsUserEmail(String e) {
        return userRepository.existsByEmail(e)
                .map(exists -> !exists);
    }

    private Mono<Void> validateEmailAuthentication(String email) {
        return Mono.just(email)
                .filterWhen(authUserRepository::existsByEmail)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .then();
    }

    private UserEntity createUserEntity(UserRegisterRequest request) {
        return UserEntity.createEntity(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                null,
                request.userInfo(),
                request.classInfos(),
                UserRole.USER
        );
    }

    private final CreateUserDocumentUseCase createUserDocumentUseCase;

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
}
