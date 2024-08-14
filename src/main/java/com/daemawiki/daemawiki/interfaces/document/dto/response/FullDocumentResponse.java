package com.daemawiki.daemawiki.interfaces.document.dto.response;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;

import java.util.List;
import java.util.Set;

public record FullDocumentResponse(
        String id,
        String title,
        String subTitle,
        List<Detail> details,
        List<Content> contents,
        Set<String> categories,
        Long viewCount,
        Long version,
        String type,
        EditDateTime dateTime,
        Editor owner,
        List<Editor> editors
) {
    public static FullDocumentResponse fromEntity(DocumentEntity entity) {
        return new FullDocumentResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getInfo().subTitle(),
                entity.getInfo().detailList().stream()
                        .map(detail -> new Detail(detail.title(), detail.content()))
                        .toList(),
                entity.getContentList().stream()
                        .map(content -> new Content(content.index(), content.title(), content.content()))
                        .toList(),
                entity.getCategoryList(),
                entity.getView(),
                entity.getVersion(),
                entity.getType().name(),
                new EditDateTime(entity.getDateTime().getCreated().toString(),
                        entity.getDateTime().getUpdated().toString()),
                new Editor(entity.getOwner().name(), entity.getOwner().userId()),
                entity.getEditorList().stream()
                        .map(editor -> new Editor(editor.name(), editor.userId()))
                        .toList()
        );
    }

    record EditDateTime(String createdAt, String lastEditedAt) {}
    record Detail(String title, String content) {}
    record Content(String index, String title, String content) {}
    record Editor(String name, String userId) {}
}