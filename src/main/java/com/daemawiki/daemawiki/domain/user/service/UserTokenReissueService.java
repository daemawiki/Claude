package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.user.dto.UserTokenReissueResponse;
import com.daemawiki.daemawiki.domain.user.usecase.UserTokenReissueUseCase;
import com.daemawiki.daemawiki.global.security.token.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTokenReissueService implements UserTokenReissueUseCase {
    @Override
    public Mono<UserTokenReissueResponse> reissue(String token) {
        return tokenizer.reissue(token)
                .map(UserTokenReissueResponse::of);
    }

    private final Tokenizer tokenizer;
}
