package com.daemawiki.daemawiki.interfaces.document.dto.response;

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
    public record EditDateTime(String createdAt, String lastEditedAt) {}
    public record Detail(String title, String content) {}
    public record Content(String index, String title, String content) {}
    public record Editor(String name, String userId) {}
}