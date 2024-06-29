package com.daemawiki.daemawiki.domain.user.service;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoDetail;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegisterService implements UserRegisterUseCase {
    @Override
    public Mono<Void> register(UserRegisterRequest request) {
        return userRepository.existsByEmail(request.email())
                .zipWith(authUserRepository.existsByEmail(request.email()))
                .flatMap(tuple -> {
                    boolean emailExists = tuple.getT1();
                    boolean isAuthenticated = tuple.getT2();

                    if (emailExists) {
                        return Mono.error(new RuntimeException()); // 이메일이 이미 사용 중일 때
                    }
                    if (!isAuthenticated) {
                        return Mono.error(new RuntimeException()); // 이메일 인증이 안됐을 때
                    }

                    return createDocumentAndSaveUser(
                            createUserEntity(request)
                    );
                });
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
        return DocumentEntity.createEntity(
                user.getName(),
                DocumentInfo.of(
                        user.getName(),
                        List.of(
                            DocumentInfoDetail.of(
                                    GENERATION_TITLE,
                                    user.getUserInfo().generation() + GENERATION_SUFFIX
                            ),
                            DocumentInfoDetail.of(
                                    MAJOR_TITLE,
                                    user.getUserInfo().major()
                            )
                        )
                ),
                DocumentType.STUDENT
        );
    }

    private static final String GENERATION_TITLE = "기수";
    private static final String GENERATION_SUFFIX = "기";
    private static final String MAJOR_TITLE = "전공";

    private final AuthUserRepository authUserRepository;
    private final DocumentRepository documentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
}
