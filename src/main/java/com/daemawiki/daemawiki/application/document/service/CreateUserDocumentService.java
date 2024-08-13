package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.CreateUserDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.model.DefaultDocumentEntityFactory;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class CreateUserDocumentService implements CreateUserDocumentUseCase {

    @Override
    public Mono<Void> create(UserEntity user) {
        return createDocumentAndGetId(user)
                .doOnNext(user::updateDocumentId)
                .flatMap(document -> userRepository.save(user))
                .then();
    }

    private Mono<String> createDocumentAndGetId(UserEntity user) {
        return documentRepository.save(createDocumentEntity(user))
                .map(DocumentEntity::getId);
    }

    private DocumentEntity createDocumentEntity(UserEntity user) {
        return DefaultDocumentEntityFactory.createStudentDocumentEntity(user);
    }

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
}
