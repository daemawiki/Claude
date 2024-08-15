package com.daemawiki.daemawiki.interfaces.document.dto.request;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;

import java.util.Set;

public record CreateDocumentRequest(
        String title,
        DocumentEntity.Type type,
        Set<String> category
) {
}
