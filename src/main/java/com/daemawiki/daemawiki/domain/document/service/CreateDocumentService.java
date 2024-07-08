package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.document.model.DefaultDocumentEntityFactory;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.CreateDocumentUseCase;
import com.daemawiki.daemawiki.domain.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    @Override
    public Mono<Void> create(CreateDocumentRequest request) {
        return currentUser.get()
                .map(user -> createDocumentEntity(request, user))
                .flatMap(documentRepository::save)
                .then();
    }

    private static DocumentEntity createDocumentEntity(CreateDocumentRequest request, UserEntity user) {
        return DefaultDocumentEntityFactory.createDocumentEntity(request, null, DocumentEditor.fromUser(user));
    }

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;
}
