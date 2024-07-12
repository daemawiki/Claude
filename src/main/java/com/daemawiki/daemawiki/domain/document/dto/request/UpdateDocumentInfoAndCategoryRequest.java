package com.daemawiki.daemawiki.domain.document.dto.request;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;

import java.util.List;

public record UpdateDocumentInfoAndCategoryRequest(
        DocumentInfo info,
        List<String> category
) {
}
