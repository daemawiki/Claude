package com.daemawiki.daemawiki.application.user.usecase;

import com.daemawiki.daemawiki.interfaces.user.dto.UserTokenReissueResponse;
import reactor.core.publisher.Mono;

public interface UserTokenReissueUseCase {
    Mono<UserTokenReissueResponse> reissue(String token);
}
