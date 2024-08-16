package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DeleteDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.model.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class DeleteDocumentService implements DeleteDocumentUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    public Mono<Void> delete(String documentId) {
        return findDocument(documentId)
                .zipWith(currentUser.get())
                .flatMap(tuple -> validateAccess(tuple.getT1(), tuple.getT2()))
                .flatMap(documentRepository::deleteById);
    }

    private Mono<DocumentEntity> findDocument(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException()));
    }

    private Mono<String> validateAccess(DocumentEntity document, UserEntity user) {
        return Mono.just(user)
                .filter(u -> document.canDelete(DocumentElementMapper.fromUserToEditor(u)))
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .thenReturn(document.getId());
    }
}
