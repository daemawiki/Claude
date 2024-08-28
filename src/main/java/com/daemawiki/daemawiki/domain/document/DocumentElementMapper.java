package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

/**
 * layer간의 dto 클래스가 Document의 요소 클래스로 변환 과정이 필요할 때 해당 Mapper 클래스를 사용합니다.
 * */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentElementMapper {

    /* to Document elements */
    DocumentEntity.Title toTitle(DocumentElementDtos.TitleDto dto);

    DocumentEntity.Detail toDetail(DocumentElementDtos.DetailDto dto);

    DocumentEntity.Editor toEditor(DocumentElementDtos.EditorDto dto);

    DocumentEntity.EditDateTime toEditDateTime(DocumentElementDtos.EditDateTimeDto dto);

    default Tuple2<List<DocumentEntity.Detail>, DocumentEntity.Title> toInfoTuple(DocumentElementDtos.InfoUpdateDto dto) {
        return Tuples.of(
                dto.detailList().stream()
                        .map(detail -> new DocumentEntity.Detail(detail.title(), detail.content()))
                        .toList(),
                new DocumentEntity.Title(dto.title().mainTitle(), dto.title().subTitle())
        );
    }

    default DocumentEntity.Editor fromUserToEditor(UserEntity user) {
        return DocumentEntity.Editor.fromUser(user);
    }

    DocumentEntity.Content toContent(DocumentElementDtos.ContentDto dto);

    DocumentEntity.Type toType(DocumentElementDtos.TypeDto typeDto);

    default List<DocumentEntity.Content> toContentList(List<DocumentElementDtos.ContentDto> dtoList) {
        return dtoList.stream()
                .map(this::toContent)
                .toList();
    }

    default List<DocumentEntity.Editor> toEditorList(List<DocumentElementDtos.EditorDto> dtoList) {
        return dtoList.stream()
                .map(this::toEditor)
                .toList();
    }

    default List<DocumentEntity.Detail> toDetailList(List<DocumentElementDtos.DetailDto> dtoList) {
        return dtoList.stream()
                .map(this::toDetail)
                .toList();
    }

    /* to dtos */
    DocumentElementDtos.TitleDto toTitleDto(DocumentEntity.Title documentTitle);

    DocumentElementDtos.DetailDto toDetailDto(DocumentEntity.Detail documentDetail);

    DocumentElementDtos.EditorDto toEditorDto(DocumentEntity.Editor documentEditor);

    default DocumentElementDtos.EditorDto fromUsertoEditorDto(UserEntity user) {
        return new DocumentElementDtos.EditorDto(user.getName(), user.getId());
    }

    DocumentElementDtos.EditDateTimeDto toEditDateTimeDto(DocumentEntity.EditDateTime documentEditDateTime);

    DocumentElementDtos.ContentDto toContentDto(DocumentEntity.Content documentContent);

    DocumentElementDtos.TypeDto toTypeDto(DocumentEntity.Type documentType);

    default List<DocumentElementDtos.ContentDto> toContentListDto(List<DocumentEntity.Content> documentContentList) {
        return documentContentList.stream()
                .map(this::toContentDto)
                .toList();
    }

    default List<DocumentElementDtos.EditorDto> toEditorListDto(List<DocumentEntity.Editor> documentEditorList) {
        return documentEditorList.stream()
                .map(this::toEditorDto)
                .toList();
    }

    default List<DocumentElementDtos.DetailDto> toDetailListDto(List<DocumentEntity.Detail> documentDetailList) {
        return documentDetailList.stream()
                .map(this::toDetailDto)
                .toList();
    }
}
