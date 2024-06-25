package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.mail.auth_user.repository.AuthUserRepository;
import com.daemawiki.daemawiki.domain.user.dto.UserRegisterRequest;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.domain.user.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {
    @Override
    public Mono<Void> register(UserRegisterRequest request) {
        return userRepository.existsByEmail(request.email())
                .filter(isExist -> !isExist)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 중복된 아메일
                .flatMap(isExist -> validateAndCreateUser(request));
    }

    private Mono<Void> validateAndCreateUser(UserRegisterRequest request) {
        return authUserRepository.findByMail(request.email())
                .filter(authenticated -> authenticated)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 메일 인증 내역 x
                .map(authenticated -> createUserEntity(request))
                .flatMap(this::createDocumentAndSaveUser);
    }

    private Mono<Void> createDocumentAndSaveUser(UserEntity user) {
        return createDocumentAndGetId(user)
                .doOnNext(user::updateDocumentId)
                .flatMap(document -> userRepository.save(user))
                .then();
    }

    private UserEntity createUserEntity(UserRegisterRequest request) {
        return UserEntity.createEntity(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                null,
                request.userInfo(),
                request.classInfos(),
                UserRole.USER
        );
    }

    private Mono<String> createDocumentAndGetId(UserEntity user) {
        return documentRepository.save(
                createDocumentEntity(user)
        ).map(DocumentEntity::getId);
    }

    private DocumentEntity createDocumentEntity(UserEntity user) {
        // 해당 유저의 문서가 이미 존재할 때 / 존재 안할 때 구분 (어떤 기준으로?)
        // save :: return id
        return DocumentEntity.createEntity(
                user.getName(),
                DocumentInfo.of(
                        user.getName(),
                        Collections.emptyList()
                ),
                DocumentType.STUDENT
        );
    }

    private final AuthUserRepository authUserRepository;
    private final DocumentRepository documentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
}
