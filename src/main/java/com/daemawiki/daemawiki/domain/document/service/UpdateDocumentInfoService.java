package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoVO;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.usecase.UpdateDocumentInfoUseCase;
import com.daemawiki.daemawiki.domain.user.component.CurrentUser;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class UpdateDocumentInfoService implements UpdateDocumentInfoUseCase {

    @Override
    public Mono<Void> update(String documentId, DocumentInfoVO request) {
        return documentRepository.findById(documentId)
                .zipWith(currentUser.get())
                .flatMap(this::validateUser)
                .doOnNext(document -> document.updateDocumentInfo(request))
                .flatMap(documentRepository::save)
                .then();
    }

    private Mono<DocumentEntity> validateUser(Tuple2<DocumentEntity, UserEntity> tuple) {
        return Mono.just(tuple)
                .filter(t -> t.getT1().canEdit(t.getT2()))
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 수정 권한 없음
                .map(Tuple2::getT1);
    }

    private final DocumentRepository documentRepository;
    private final CurrentUser currentUser;
}
