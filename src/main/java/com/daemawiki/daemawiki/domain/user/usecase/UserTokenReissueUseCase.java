package com.daemawiki.daemawiki.domain.user.usecase;

import com.daemawiki.daemawiki.domain.user.dto.UserTokenReissueResponse;
import reactor.core.publisher.Mono;

public interface UserTokenReissueUseCase {
    Mono<UserTokenReissueResponse> reissue(String token);
}
