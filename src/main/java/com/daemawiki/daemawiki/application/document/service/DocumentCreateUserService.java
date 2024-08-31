package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.CreateUserDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.DocumentDefaultModelFactory;
import com.daemawiki.daemawiki.domain.document.DocumentModel;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class DocumentCreateUserService implements CreateUserDocumentUseCase {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Mono<Void> create(UserEntity user) {
        return createDocumentAndGetId(user)
                .doOnNext(user::updateDocumentId)
                .flatMap(document -> userRepository.save(user))
                .then();
    }

    private Mono<String> createDocumentAndGetId(UserEntity user) {
        return documentRepository.save(createDocumentEntity(user))
                .map(DocumentModel::id);
    }

    private DocumentModel createDocumentEntity(UserEntity user) {
        return DocumentDefaultModelFactory.createDocumentModelByStudentEntity(user);
    }
}
