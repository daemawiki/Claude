package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Set;

public record DocumentModel(
        String id,

        DocumentElementDtos.Title title,

        List<DocumentElementDtos.Detail> detailList,

        String content,

        Set<String> categorySet,

        Long viewCount,

        Long version,

        DocumentElementDtos.Type type,

        DocumentElementDtos.EditDateTime dateTime,

        DocumentElementDtos.Editor owner,

        List<DocumentElementDtos.Editor> editorList
) {
    public DocumentModel(DocumentElementDtos.Title title, List<DocumentElementDtos.Detail> detailList, Set<String> categoryList, DocumentElementDtos.Type type, DocumentElementDtos.Editor owner) {
        this(null, title, detailList, null, categoryList, 0L, 0L, type, null, owner, null);
    }

    // TODO: 8/22/24 불변 record setter 올바르게 작성하기

    public boolean canEdit(DocumentElementDtos.Editor editor) { // TODO: 8/13/24 DSM_MOP 검사 로직 옮기기
        return isOwner(editor) || editorList.contains(editor);
    }

    public boolean canDelete(DocumentElementDtos.Editor editor) {
        return canEdit(editor) && !type.equals(DocumentEntity.Type.STUDENT);
    }

    public boolean isOwner(DocumentElementDtos.Editor editor) {
        return owner.equals(editor);
    }

    /* setters */

    public DocumentModel updateEditors(List<DocumentElementDtos.Editor> editorList) {
        return new DocumentModel(id, title, detailList, content, categorySet, viewCount, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel updateContents(String content) {
        return new DocumentModel(id, title, detailList, content, categorySet, viewCount, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel updateDocumentInfo(Tuple2<List<DocumentElementDtos.Detail>, DocumentElementDtos.Title> tuple) {
        return new DocumentModel(id, tuple.getT2(), tuple.getT1(), content, categorySet, viewCount, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel increaseView() {
        return new DocumentModel(id, title, detailList, content, categorySet, viewCount + 1, version, type, dateTime, owner, editorList);
    }
}
