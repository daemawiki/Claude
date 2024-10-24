package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentCreateUseCase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.document.DocumentDefaultModelFactory;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentModel;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
class DocumentCreateService implements DocumentCreateUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    public Mono<Void> create(DocumentCreateRequest request) {
        return currentUser.get()
                .map(user -> createDocumentEntity(request, user))
                .flatMap(documentRepository::save)
                .then();
    }

    private static DocumentModel createDocumentEntity(DocumentCreateRequest request, UserEntity user) {
        return DocumentDefaultModelFactory.createDocumentEntity(
                request.title(),
                Collections.emptyList(),
                request.category(),
                request.type(),
                DocumentElementMapper.fromUserToEditorDto(user)
        );
    }
}
