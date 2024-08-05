package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.application.user.service.UserTokenReissueService;
import com.daemawiki.daemawiki.interfaces.user.dto.UserTokenReissueResponse;
import com.daemawiki.daemawiki.common.security.token.Tokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserTokenReissueServiceTest {
    @InjectMocks
    private UserTokenReissueService userTokenReissueService;
    @Mock
    private Tokenizer tokenizer;
    private static final String oldToken = "old token";
    private static final String newToken = "new token";

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("토큰 재발급 성공")
    void token_reissue_success() {
        var response = UserTokenReissueResponse.of(newToken);

        when(tokenizer.reissue(anyString()))
                .thenReturn(Mono.just(newToken));

        StepVerifier.create(userTokenReissueService.reissue(oldToken))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    @DisplayName("토큰 재발급 실패")
    void token_reissue_fails() {
        when(tokenizer.reissue(anyString()))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(userTokenReissueService.reissue(oldToken))
                .verifyError();
    }
}
