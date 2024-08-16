package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;

public class DocumentEntityMapper {

    public static FullDocumentResponse toFullResponse(DocumentEntity entity) {
        return new FullDocumentResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getInfo().subTitle(),
                entity.getInfo().detailList().stream()
                        .map(detail -> new FullDocumentResponse.Detail(detail.title(), detail.content()))
                        .toList(),
                entity.getContentList().stream()
                        .map(content -> new FullDocumentResponse.Content(content.index(), content.title(), content.content()))
                        .toList(),
                entity.getCategoryList(),
                entity.getView(),
                entity.getVersion(),
                entity.getType().name(),
                new FullDocumentResponse.EditDateTime(entity.getDateTime().getCreated().toString(),
                        entity.getDateTime().getUpdated().toString()),
                new FullDocumentResponse.Editor(entity.getOwner().name(), entity.getOwner().userId()),
                entity.getEditorList().stream()
                        .map(editor -> new FullDocumentResponse.Editor(editor.name(), editor.userId()))
                        .toList()
        );
    }
}
