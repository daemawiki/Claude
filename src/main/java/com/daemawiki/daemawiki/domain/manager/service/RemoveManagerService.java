package com.daemawiki.daemawiki.domain.manager.service;

import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import com.daemawiki.daemawiki.domain.manager.repository.ManagerRepository;
import com.daemawiki.daemawiki.domain.manager.usecase.RemoveManagerUseCase;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RemoveManagerService implements RemoveManagerUseCase {

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
                .doOnNext(user -> user.updateUserRole(UserRole.DSM_MOP))
                .flatMap(userRepository::save)
                .thenReturn(manager);
    }

    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
}
