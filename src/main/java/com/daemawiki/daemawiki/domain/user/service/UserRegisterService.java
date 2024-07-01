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
        return userRepository.existsByEmail(request.email())
                .zipWith(authUserRepository.existsByEmail(request.email()))
                .flatMap(tuple -> {
                    boolean emailExists = tuple.getT1();
                    boolean isAuthenticated = tuple.getT2();

                    if (emailExists) { // 이메일이 이미 사용 중일 때
                        return Mono.error(new RuntimeException("Email already in use."));
                    }
                    if (!isAuthenticated) { // 이메일 인증이 안됐을 때
                        return Mono.error(new RuntimeException("Email is not authenticated."));
                    }

                    return createUserDocumentUseCase.create(
                            createUserEntity(request)
                    );
                });
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
