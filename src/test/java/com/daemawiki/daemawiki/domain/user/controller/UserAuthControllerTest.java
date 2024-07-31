package com.daemawiki.daemawiki.domain.user.controller;

import com.daemawiki.daemawiki.domain.user.dto.UserLoginRequest;
import com.daemawiki.daemawiki.domain.user.dto.UserLoginResponse;
import com.daemawiki.daemawiki.domain.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.domain.user.dto.UserTokenReissueResponse;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.usecase.UserLoginUseCase;
import com.daemawiki.daemawiki.domain.user.usecase.UserRegisterUseCase;
import com.daemawiki.daemawiki.domain.user.usecase.UserTokenReissueUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserAuthControllerTest {
    private WebTestClient webTestClient;

    @Mock
    private UserLoginUseCase loginUseCase;
    @Mock
    private UserRegisterUseCase registerUseCase;
    @Mock
    private UserTokenReissueUseCase tokenReissueUseCase;

    @InjectMocks
    private UserAuthController userAuthController;

    private static final UserLoginRequest loginRequest = new UserLoginRequest("test@dsm.hs.kr", "testPassword");
    private static final UserRegisterRequest registerRequest = new UserRegisterRequest("kim", "test@dsm.hs.kr", "testPassword", null, null);

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(userAuthController).build();
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        var response = UserLoginResponse.of("token", "name", UserRole.USER);

        when(loginUseCase.login(any(UserLoginRequest.class)))
                .thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserLoginResponse.class)
                .isEqualTo(response);
    }

    @Test
    @DisplayName("로그인 실패")
    void login_fails() {
        when(loginUseCase.login(any(UserLoginRequest.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.post()
                .uri("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("회원가입 성공")
    void register_success() {
        when(registerUseCase.register(any(UserRegisterRequest.class)))
                .thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("회원가입 실패")
    void register_fails() {
        when(registerUseCase.register(any(UserRegisterRequest.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.post()
                .uri("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerRequest)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void reissue_success() {
        var requestHeader = "oldToken";
        var response = UserTokenReissueResponse.of("newToken");

        when(tokenReissueUseCase.reissue(anyString()))
                .thenReturn(Mono.just(response));

        webTestClient.put()
                .uri("/api/auth/reissue")
                .header(HttpHeaders.AUTHORIZATION, requestHeader)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserTokenReissueResponse.class)
                .isEqualTo(response);
    }

    @Test
    @DisplayName("토큰 재발급 실패-server")
    void reissue_fails_with_header() {
        var requestHeader = "oldToken";

        when(tokenReissueUseCase.reissue(anyString()))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.put()
                .uri("/api/auth/reissue")
                .header(HttpHeaders.AUTHORIZATION, requestHeader)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("토큰 재발급 실패-client without auth header")
    void reissue_fails_without_header() {
        when(tokenReissueUseCase.reissue(any(String.class)))
                .thenReturn(Mono.error(new RuntimeException()));

        webTestClient.put()
                .uri("/api/auth/reissue")
                .exchange()
                .expectStatus().is4xxClientError();
    }
}
