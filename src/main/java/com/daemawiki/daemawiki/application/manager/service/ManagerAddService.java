package com.daemawiki.daemawiki.application.manager.service;

import com.daemawiki.daemawiki.application.manager.usecase.ManagerAddUseCase;
import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import com.daemawiki.daemawiki.domain.manager.repository.ManagerRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 관리자 추가 서비스 구현 클래스
 */
@Service
@RequiredArgsConstructor
class ManagerAddService implements ManagerAddUseCase {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;

    /**
     * 관리자 추가 메서드
     *
     * @param email 기존 관리자에게 입력 받은 새관리자의 이메일
     */
    @Override
    public Mono<Void> add(String email) {
        return userRepository.findByEmail(email)
                .flatMap(this::updateUserRoleAndSave)
                .flatMap(user -> saveManager(email, user.getId()))
                .switchIfEmpty(Mono.defer(() -> saveManager(email, "")))
                .then();
    }

    /**
     * 이미 가입된 유저라면 역할 변경 (이전의 역할 -> Manager)
     * <s>신분 상승</s>
     *
     * @param user 유저 엔티티
     * @return Mono<UserEntity> UserEntity 유저 엔티티
     */
    private Mono<UserEntity> updateUserRoleAndSave(UserEntity user) {
        user.setRoleToMANAGER();
        return userRepository.save(user);
    }

    /**
     * DB에 Manager 정보 저장
     *
     * @param email  기존 관리자에게 입력 받은 새관리자의 이메일
     * @param userId 새로운 관리자의 userId
     * @return Mono<ManagerEntity> ManagerEntity 관리자 엔티티
     */
    private Mono<ManagerEntity> saveManager(String email, String userId) {
        return managerRepository.save(ManagerEntity.of(email, userId));
    }
}