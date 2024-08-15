package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.interfaces.document.dto.request.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.document.model.DefaultDocumentEntityFactory;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.document.CreateDocumentUseCase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class CreateDocumentService implements CreateDocumentUseCase {

    @Override
    public Mono<Void> create(CreateDocumentRequest request) {
        return currentUser.get()
                .map(user -> createDocumentEntity(request, user))
                .flatMap(documentRepository::save)
                .then();
    }

    private static DocumentEntity createDocumentEntity(CreateDocumentRequest request, UserEntity user) {
        return DefaultDocumentEntityFactory.createDocumentEntity(request, null, DocumentEntity.Editor.fromUser(user));
    }

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;
}
