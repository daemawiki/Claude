package com.daemawiki.daemawiki.application.user.service;

import com.daemawiki.daemawiki.application.user.usecase.UserTokenReissueUseCase;
import com.daemawiki.daemawiki.interfaces.user.dto.UserTokenReissueResponse;
import com.daemawiki.daemawiki.common.security.token.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 토큰 재발급 서비스 구현 클래스
 */
@Service
@RequiredArgsConstructor
public class UserTokenReissueService implements UserTokenReissueUseCase {

    /**
     * 토큰 재발급 메서드
     *
     * @param token 사용자에게 입력받은 기존의 토큰
     * @return Mono<UserTokenReissueResponse> UserTokenReissueResponse 새로 발급된 토큰
     */
    @Override
    public Mono<UserTokenReissueResponse> reissue(String token) {
        return tokenizer.reissue(token)
                .map(UserTokenReissueResponse::of);
    }

    private final Tokenizer tokenizer;
}
