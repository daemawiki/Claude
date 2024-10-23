package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;

import java.util.Set;

public record DocumentSimpleResult(
        String id,
        String type,
        String title,
        String content,
        Set<String> category,
        Long view,
        DocumentElementDtos.EditDateTime dateTime
) {
    public static DocumentSimpleResult fromDocumentModel(DocumentModel model) {
        return new DocumentSimpleResult(
                model.id(),
                model.type().name(),
                model.title().mainTitle(),
                model.content(),
                model.categorySet(),
                model.viewCount(),
                model.dateTime()
        );
    }
}
