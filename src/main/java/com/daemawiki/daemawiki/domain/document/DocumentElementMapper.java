package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * layer간의 dto 클래스가 Document의 요소 클래스로 변환 과정이 필요할 때 해당 Mapper 클래스를 사용합니다.
 * */
public class DocumentElementMapper {

    /* to Document elements */
    public static DocumentEntity.Title toTitle(DocumentElementDtos.TitleDto dto) {
        return new DocumentEntity.Title(dto.mainTitle(), dto.subTitle());
    }

    public static DocumentEntity.Detail toDetail(DocumentElementDtos.DetailDto dto) {
        return new DocumentEntity.Detail(dto.title(), dto.content());
    }

    public static DocumentEntity.Editor toEditor(DocumentElementDtos.EditorDto dto) {
        return new DocumentEntity.Editor(dto.name(), dto.userId());
    }

    public static DocumentEntity.EditDateTime toEditDateTime(DocumentElementDtos.EditDateTimeDto dto) {
        return new DocumentEntity.EditDateTime(dto.createdDateTime(), dto.lastModifiedDateTime());
    }

    public static Tuple2<List<DocumentEntity.Detail>, DocumentEntity.Title> toInfoTuple(DocumentElementDtos.InfoUpdateDto dto) {
        return Tuples.of(
                dto.detailList().stream()
                        .map(detail -> new DocumentEntity.Detail(detail.title(), detail.content()))
                        .toList(),
                new DocumentEntity.Title(dto.title().mainTitle(), dto.title().subTitle())
        );
    }

    public static DocumentEntity.Content toContent(DocumentElementDtos.ContentDto dto) {
        return new DocumentEntity.Content(dto.index(), dto.title(), dto.content());
    }

    public static DocumentEntity.Editor fromUserToEditor(UserEntity user) {
        return DocumentEntity.Editor.fromUser(user);
    }

    public static DocumentEntity.Type toType(DocumentElementDtos.TypeDto typeDto) {
        return DocumentEntity.Type.valueOf(typeDto.name());
    }

    public static List<DocumentEntity.Content> toContentList(List<DocumentElementDtos.ContentDto> dtoList) {
        return dtoList.stream()
                .map(DocumentElementMapper::toContent)
                .toList();
    }

    public static List<DocumentEntity.Editor> toEditorList(List<DocumentElementDtos.EditorDto> dtoList) {
        return dtoList.stream()
                .map(DocumentElementMapper::toEditor)
                .toList();
    }

    public static List<DocumentEntity.Detail> toDetailList(List<DocumentElementDtos.DetailDto> dtoList) {
        return dtoList.stream()
                .map(DocumentElementMapper::toDetail)
                .toList();
    }

    /* to dtos */
    public static DocumentElementDtos.TitleDto toTitleDto(DocumentEntity.Title documentTitle) {
        return new DocumentElementDtos.TitleDto(documentTitle.mainTitle(), documentTitle.subTitle());
    }

    public static DocumentElementDtos.DetailDto toDetailDto(DocumentEntity.Detail documentDetail) {
        return new DocumentElementDtos.DetailDto(documentDetail.title(), documentDetail.content());
    }

    public static DocumentElementDtos.EditorDto toEditorDto(DocumentEntity.Editor documentEditor) {
        return new DocumentElementDtos.EditorDto(documentEditor.name(), documentEditor.userId());
    }

    public static DocumentElementDtos.EditorDto fromUsertoEditorDto(UserEntity user) {
        return new DocumentElementDtos.EditorDto(user.getName(), user.getId());
    }

    public static DocumentElementDtos.EditDateTimeDto toEditDateTimeDto(DocumentEntity.EditDateTime documentEditDateTime) {
        return new DocumentElementDtos.EditDateTimeDto(documentEditDateTime.createdDateTime(), documentEditDateTime.lastModifiedDateTime());
    }

    public static DocumentElementDtos.ContentDto toContentDto(DocumentEntity.Content documentContent) {
        return new DocumentElementDtos.ContentDto(documentContent.index(), documentContent.title(), documentContent.content());
    }

    public static DocumentElementDtos.TypeDto toTypeDto(DocumentEntity.Type documentType) {
        return DocumentElementDtos.TypeDto.valueOf(documentType.name());
    }

    public static List<DocumentElementDtos.ContentDto> toContentListDto(List<DocumentEntity.Content> documentContentList) {
        return documentContentList.stream()
                .map(DocumentElementMapper::toContentDto)
                .toList();
    }

    public static List<DocumentElementDtos.EditorDto> toEditorListDto(List<DocumentEntity.Editor> documentEditorList) {
        return documentEditorList.stream()
                .map(DocumentElementMapper::toEditorDto)
                .toList();
    }

    public static List<DocumentElementDtos.DetailDto> toDetailListDto(List<DocumentEntity.Detail> documentDetailList) {
        return documentDetailList.stream()
                .map(DocumentElementMapper::toDetailDto)
                .toList();
    }
}
