package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.internal.DocumentEditorInfo;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;

import java.util.List;

/**
 * <pre>
 * DocumentInfoDto     to  DocumentEntity.Info
 * DocumentContentDto  to  DocumentEntity.Content
 * DocumentEditorInfo  to  DocumentEntity.Editor
 * String              to  DocumentEntity.Type
 *
 * application / business layer에서
 * Document의 요소로 변환 과정이 필요할 때 해당 Mapper 클래스를 사용합니다.<pre/>
 * */
public class DocumentElementMapper {

    public static DocumentEntity.Info toInfo(DocumentElementDtos.DocumentInfoDto dto) {
        List<DocumentEntity.Info.Detail> details = dto.details().stream()
                .map(detailDto -> new DocumentEntity.Info.Detail(detailDto.title(), detailDto.content()))
                .toList();
        return new DocumentEntity.Info(dto.subTitle(), details);
    }

    public static DocumentEntity.Content toContent(DocumentElementDtos.DocumentContentDto dto) {
        return new DocumentEntity.Content(dto.index(), dto.title(), dto.content());
    }

    public static DocumentEntity.Editor fromUserToEditor(UserEntity user) {
        return DocumentEntity.Editor.fromUser(user);
    }

    public static DocumentEntity.Editor toEditor(DocumentEditorInfo info) {
        return new DocumentEntity.Editor(info.name(), info.userId());
    }

    public static DocumentEntity.Type toType(String typeString) {
        return DocumentEntity.Type.valueOf(typeString.toUpperCase());
    }

    public static List<DocumentEntity.Content> toContentList(List<DocumentElementDtos.DocumentContentDto> dtoList) {
        return dtoList.stream()
                .map(DocumentElementMapper::toContent)
                .toList();
    }

    public static List<DocumentEntity.Editor> toEditorList(List<DocumentEditorInfo> infoList) {
        return infoList.stream()
                .map(DocumentElementMapper::toEditor)
                .toList();
    }
}
