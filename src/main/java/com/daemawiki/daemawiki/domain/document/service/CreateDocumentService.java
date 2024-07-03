package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.document.model.DefaultDocumentEntityFactory;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.CreateDocumentUseCase;
import com.daemawiki.daemawiki.domain.user.component.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreateDocumentService implements CreateDocumentUseCase {

    @Override
    public Mono<Void> create(CreateDocumentRequest request) {
        return currentUser.get()
                .map(user -> DefaultDocumentEntityFactory.createAnyDocument(request, DocumentEditor.fromUser(user)))
                .flatMap(documentRepository::save)
                .then();
    }

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;
}
