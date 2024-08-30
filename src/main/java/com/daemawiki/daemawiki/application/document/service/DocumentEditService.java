package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentEditUseCase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentModel;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
class DocumentEditService implements DocumentEditUseCase {
    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;

    @Override
    @Transactional
    public Mono<Void> editContents(String documentId, List<DocumentElementDtos.Content> updateData) {
        return updateDocument(
                documentId,
                updateData,
                DocumentModel::updateContents
        );
    }

    @Override
    public Mono<Void> editInfo(String documentId, DocumentElementDtos.UpdateInfo request) {
        return updateDocument(
                documentId,
                request,
                (document, dto) -> document.updateDocumentInfo(
                        Tuples.of(dto.detailList(), dto.title())
                )
        );
    }

    @Override
    public Mono<Void> editEditors(String documentId, List<DocumentElementDtos.Editor> updateData) {
        return updateDocument(
                documentId,
                updateData,
                DocumentModel::updateEditors
        );
    }

    private <T> Mono<Void> updateDocument(String documentId, T updateData, BiConsumer<DocumentModel, T> updateFunction) {
        return documentRepository.findById(documentId)
                .zipWith(currentUser.get())
                .flatMap(this::validateAccess)
                .doOnNext(document -> updateFunction.accept(document, updateData))
                .flatMap(documentRepository::save)
                .then();
    }

    private Mono<DocumentModel> validateAccess(Tuple2<DocumentModel, UserEntity> tuple) {
        return Mono.just(tuple)
                .filter(t -> t.getT1().canEdit(DocumentElementMapper.fromUserToEditorDto(t.getT2())))
                .switchIfEmpty(Mono.error(new RuntimeException("궎한 없거요")))
                .map(Tuple2::getT1);
    }
}
