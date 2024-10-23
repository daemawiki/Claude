package com.daemawiki.daemawiki.interfaces.document.dto.response;

import com.daemawiki.daemawiki.common.annotation.Factory;
import com.daemawiki.daemawiki.domain.document.DocumentModel;

import java.util.List;
import java.util.Set;

public record DocumentFullResponse(
        String id,
        Title title,
        List<Detail> details,
        String content,
        Set<String> categories,
        Long viewCount,
        Long version,
        String type,
        EditDateTime dateTime,
        Editor owner,
        List<Editor> editors
) {
    @Factory
    public static DocumentFullResponse createNewInstanceFromDocumentModel(DocumentModel model) {
        return new DocumentFullResponse(
                model.id(),
                new Title(model.title().mainTitle(), model.title().subTitle()),
                model.detailList().stream()
                        .map(detail -> new Detail(detail.title(), detail.content()))
                        .toList(),
                model.content(),
                model.categorySet(),
                model.viewCount(),
                model.version(),
                model.type().name(),
                new EditDateTime(model.dateTime().createdDateTime().toString(), model.dateTime().lastModifiedDateTime().toString()),
                new Editor(model.owner().name(), model.owner().userId()),
                model.editorList().stream()
                        .map(editor -> new Editor(editor.name(), editor.userId()))
                        .toList()
        );
    }

    record Title(String mainTitle, String subTitle) {}
    record EditDateTime(String createdDateTime, String lastModifiedDateTime) {}
    record Detail(String title, String content) {}
    record Editor(String name, String userId) {}
}