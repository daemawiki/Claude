package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.user.dto.UserReissueResponse;
import com.daemawiki.daemawiki.domain.user.usecase.UserReissueUseCase;
import com.daemawiki.daemawiki.global.security.token.Tokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserReissueService implements UserReissueUseCase {
    @Override
    public Mono<UserReissueResponse> reissue(String token) {
        return tokenizer.reissue(token)
                .map(UserReissueResponse::of);
    }

    private final Tokenizer tokenizer;
}
