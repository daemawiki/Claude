package com.daemawiki.daemawiki.domain.user.usecase;

import com.daemawiki.daemawiki.domain.user.dto.UserReissueResponse;
import reactor.core.publisher.Mono;

public interface UserReissueUseCase {
    Mono<UserReissueResponse> reissue(String token);
}
