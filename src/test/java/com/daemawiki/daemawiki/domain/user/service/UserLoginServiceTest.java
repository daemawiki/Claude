package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.application.user.service.UserLoginService;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.interfaces.user.dto.UserLoginResponse;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.common.security.token.Tokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class UserLoginServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Tokenizer tokenizer;

    @InjectMocks
    private UserLoginService userLoginService;

    private static final UserLoginRequest loginRequest = new UserLoginRequest("test@dsm.hs.kr", "password");
    private static final UserEntity user = UserEntity.createEntity("kim", "test@dsm.hs.kr", "hashedPassword", "", null, null, UserRole.USER);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("로그인 성공 케이스")
    void login_success() {
        var token = "token";

        when(userRepository.findByEmail(loginRequest.email()))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches(loginRequest.password(), "hashedPassword"))
                .thenReturn(true);

        when(tokenizer.createToken(loginRequest.email()))
                .thenReturn(Mono.just(token));

        StepVerifier.create(userLoginService.login(loginRequest))
                .expectNext(UserLoginResponse.of(token, "kim", UserRole.USER))
                .verifyComplete();
    }

    @Test
    @DisplayName("로그인 실패 케이스-Not Found")
    void login_user_not_found() {
        var request = new UserLoginRequest("non-exists@dsm.hs.kr", "password");

        when(userRepository.findByEmail(request.email()))
                .thenReturn(Mono.empty());

        StepVerifier.create(userLoginService.login(request))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("로그인 실패 케이스-Invalid Password")
    void login_invalid_password() {
        when(userRepository.findByEmail(loginRequest.email()))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches("wrong password", "pw"))
                .thenReturn(false);

        StepVerifier.create(userLoginService.login(loginRequest))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("로그인 실패 케이스-Token Creation Fails")
    void login_token_creation_fails() {
        when(userRepository.findByEmail(loginRequest.email()))
                .thenReturn(Mono.just(user));

        when(passwordEncoder.matches(loginRequest.password(), "pw"))
                .thenReturn(true);

        when(tokenizer.createToken(loginRequest.email()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(userLoginService.login(loginRequest))
                .expectError(RuntimeException.class)
                .verify();
    }
}