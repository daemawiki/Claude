package com.daemawiki.daemawiki.application.manager.service;

import com.daemawiki.daemawiki.application.manager.usecase.ManagerRemoveUseCase;
import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import com.daemawiki.daemawiki.domain.manager.repository.ManagerRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class ManagerRemoveService implements ManagerRemoveUseCase {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    @Override
    public Mono<Void> remove(String email) {
        return managerRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 해당 이메일이 관리자가 아닐 때
                .filter(this::isExistsUser)
                .flatMap(this::lossOfRole)
                .zipWhen(managerRepository::delete)
                .then();
    }

    private boolean isExistsUser(ManagerEntity manager) {
        return !manager.getUserId().isBlank();
    }

    private Mono<ManagerEntity> lossOfRole(ManagerEntity manager) {
        return userRepository.findById(manager.getId())
                .doOnNext(UserEntity::setRoleToDSM_MOP)
                .flatMap(userRepository::save)
                .thenReturn(manager);
    }
}
