package com.daemawiki.daemawiki.application.document.service.base;

import com.daemawiki.daemawiki.domain.document.model.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.BiConsumer;

@RequiredArgsConstructor
public abstract class AbstractDocumentUpdateServiceBase<T> {

    protected Mono<Void> updateDocument(String documentId, T updateData, BiConsumer<DocumentEntity, T> updateFunction) {
        return documentRepository.findById(documentId)
                .zipWith(currentUser.get())
                .flatMap(this::validateAccess)
                .doOnNext(document -> updateFunction.accept(document, updateData))
                .flatMap(documentRepository::save)
                .then();
    }

    private Mono<DocumentEntity> validateAccess(Tuple2<DocumentEntity, UserEntity> tuple) {
        return Mono.just(tuple)
                .filter(t -> t.getT1().canEdit(DocumentElementMapper.fromUserToEditor(t.getT2())))
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 수정 권한 x
                .map(Tuple2::getT1);
    }

    protected final DocumentRepository documentRepository;
    protected final CurrentUser currentUser;
}
