package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Set;

public record DocumentModel(
        String id,

        DocumentElementDtos.TitleDto title,

        List<DocumentElementDtos.DetailDto> detailList,

        List<DocumentElementDtos.ContentDto> contentList,

        Set<String> categoryList,

        Long view,

        Long version,

        DocumentElementDtos.TypeDto type,

        DocumentElementDtos.EditDatetimeDto dateTime,

        DocumentElementDtos.EditorDto owner,

        List<DocumentElementDtos.EditorDto> editorList
) {

    // TODO: 8/22/24 불변 record setter 올바르게 작성하기

    public boolean canEdit(DocumentElementDtos.EditorDto editor) { // TODO: 8/13/24 DSM_MOP 검사 로직 옮기기
        return isOwner(editor) || editorList.contains(editor);
    }

    public boolean canDelete(DocumentElementDtos.EditorDto editor) {
        return canEdit(editor) && !type.equals(DocumentEntity.Type.STUDENT);
    }

    public boolean isOwner(DocumentElementDtos.EditorDto editor) {
        return owner.equals(editor);
    }

    /* setters */

    public DocumentModel updateEditors(List<DocumentElementDtos.EditorDto> editorList) {
        return new DocumentModel(id, title, detailList, contentList, categoryList, view, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel updateContents(List<DocumentElementDtos.ContentDto> contentList) {
        return new DocumentModel(id, title, detailList, contentList, categoryList, view, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel updateDocumentInfo(Tuple2<List<DocumentElementDtos.DetailDto>, DocumentElementDtos.TitleDto> tuple) {
        return new DocumentModel(id, tuple.getT2(), tuple.getT1(), contentList, categoryList, view, version + 1, type, dateTime, owner, editorList);
    }

    public DocumentModel increaseView() {
        return new DocumentModel(id, title, detailList, contentList, categoryList, view + 1, version, type, dateTime, owner, editorList);
    }
}
