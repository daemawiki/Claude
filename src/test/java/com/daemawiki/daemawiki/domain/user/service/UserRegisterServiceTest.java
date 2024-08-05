package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.application.document.usecase.CreateUserDocumentUseCase;
import com.daemawiki.daemawiki.application.user.service.UserRegisterService;
import com.daemawiki.daemawiki.domain.mail.repository.AuthUserRepository;
import com.daemawiki.daemawiki.interfaces.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserRegisterServiceTest {
    @Mock
    private CreateUserDocumentUseCase createUserDocumentUseCase;
    @Mock
    private AuthUserRepository authUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRegisterService userRegisterService;

    private static final UserRegisterRequest registerRequest = new UserRegisterRequest("kim", "test@dsm.hs.kr", "pw", null, null);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        when(userRepository.existsByEmail(registerRequest.email()))
                .thenReturn(Mono.just(false));

        when(authUserRepository.existsByEmail(registerRequest.email()))
                .thenReturn(Mono.just(true));

        when(passwordEncoder.encode(registerRequest.password()))
                .thenReturn("hpw");

        when(createUserDocumentUseCase.create(any(UserEntity.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(userRegisterService.register(registerRequest))
                .verifyComplete();
    }

    @Test
    @DisplayName("회원가입 실패-already exist email")
    void register_fails_already_exist_email() {
        when(userRepository.existsByEmail(registerRequest.email()))
                .thenReturn(Mono.just(true));

        when(authUserRepository.existsByEmail(registerRequest.email()))
                .thenReturn(Mono.just(true));

        when(passwordEncoder.encode(registerRequest.password()))
                .thenReturn("hpw");

        when(createUserDocumentUseCase.create(any(UserEntity.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(userRegisterService.register(registerRequest))
                .verifyError();
    }
}
