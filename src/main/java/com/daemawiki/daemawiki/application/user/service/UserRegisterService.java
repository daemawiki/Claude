package com.daemawiki.daemawiki.application.user.service;

import com.daemawiki.daemawiki.application.document.CreateUserDocumentUseCase;
import com.daemawiki.daemawiki.domain.mail.repository.AuthUserRepository;
import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import com.daemawiki.daemawiki.domain.manager.repository.ManagerRepository;
import com.daemawiki.daemawiki.interfaces.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.application.user.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 사용자 회원가입 서비스 구현 클래스 <br/>
 * 사용자 정보 저장
 */
@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {
    /**
     * 회원가입 메서드<br/>
     * validateRegistration 메서드에서 에러 signal을 보내어 then이 작동 안하는 상황이 있어 <br/>
     * Mono.defer을 사용해 지연 처리
     *
     * @param request 회원가입 요청 dto
     * @return
     */
    @Override
    public Mono<Void> register(UserRegisterRequest request) {
        return validateRegistration(request)
                .then(Mono.defer(() -> executeRegisterProcess(request)));
    }

    /**
     * 회원가입 요청 검증 메서드 <br/>
     * Mono.when을 사용하여 병렬 실행
     *
     * @param request 회원가입 요청 dto
     * @exception
     */
    private Mono<Void> validateRegistration(UserRegisterRequest request) {
        return Mono.when(
                ensureEmailNotRegistered(request.email()),
                validateEmailAuthentication(request.email())
        );
    }

    /**
     * 해당 이메일로 가입된 유저가 있는 지 검증 메서드
     *
     * @param email 회원가입 요청 바디의 email 필드
     * @exception
     */
    private Mono<Void> ensureEmailNotRegistered(String email) {
        return userRepository.existsByEmail(email)
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .then();
    }

    /**
     * 해당 이메일이 DSM 메일 인증이 된 이메일인지 검증 메서드
     *
     * @param email 회원가입 요청 바디의 email 필드
     * @exception
     */
    private Mono<Void> validateEmailAuthentication(String email) {
        return authUserRepository.existsByEmail(email)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .then();
    }

    private Mono<Void> executeRegisterProcess(UserRegisterRequest request) {
        return createUserWithRole(request)
                .flatMap(createUserDocumentUseCase::create);
    }

    /**
     * 사용자 역할에 따른 유저 생성 메서드에 맵핑해주는 메서드 <br/>
     * manager에 저장이 되어있으면 -> 관리자 createManagerUser <br/>
     * 없다면 -> 일반 유저 createRegularUser
     *
     * @param request 회원가입 요청 바디의 email 필드
     * @return Mono<UserEntity> UserEntity 생성된 유저 엔티티
     */
    private Mono<UserEntity> createUserWithRole(UserRegisterRequest request) {
        return managerRepository.findByEmail(request.email())
                .flatMap(manager -> createManagerUser(request, manager))
                .switchIfEmpty(Mono.defer(() -> createRegularUser(request)));
    }

    /**
     * 관리자 유저 생성 메서드
     *
     * @param request 회원가입 요청 dto
     * @param manager DB에서 조회한 Manager 엔티티
     * @return Mono<UserEntity> UserEntity 생성된 유저 엔티티
     */
    private Mono<UserEntity> createManagerUser(UserRegisterRequest request, ManagerEntity manager) {
        return saveUserAndUpdateManager(createUserEntity(request, UserRole.MANAGER), manager);
    }

    /**
     * 일반 유저 생성 메서드
     *
     * @param request 회원가입 요청 dto
     * @return Mono<UserEntity> UserEntity 생성된 유저 엔티티
     */
    private Mono<UserEntity> createRegularUser(UserRegisterRequest request) {
        return userRepository.save(createUserEntity(request, UserRole.USER));
    }

    /**
     *
     * @param user createUserEntity 메서드를 통해 생성된 유저 엔티티 without mongoid
     * @param manager DB에서 조회한 Manager 엔티티
     * @return Mono<UserEntity> UserEntity DB에 저장된 유저 엔티티 with mongoid
     */
    private Mono<UserEntity> saveUserAndUpdateManager(UserEntity user, ManagerEntity manager) {
        return userRepository.save(user)
                .doOnNext(savedUser -> manager.addUserId(savedUser.getId()))
                .then(managerRepository.save(manager))
                .thenReturn(user);
    }

    private UserEntity createUserEntity(UserRegisterRequest request, UserRole role) {
        return UserEntity.createEntity(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                null,
                request.userInfo(),
                request.classInfos(),
                role
        );
    }

    private final CreateUserDocumentUseCase createUserDocumentUseCase;
    private final AuthUserRepository authUserRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
}
