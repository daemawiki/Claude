package com.daemawiki.daemawiki.domain.document;

public class DocumentEntityMapper {

    public static DocumentEntity toEntity(DocumentModel document) {
        return new DocumentEntity(
                document.id(),
                DocumentElementMapper.toTitle(document.title()),
                DocumentElementMapper.toDetailList(document.detailList()),
                DocumentElementMapper.toContentList(document.contentList()),
                document.categoryList(),
                document.view(),
                document.version(),
                DocumentElementMapper.toType(document.type()),
                DocumentElementMapper.toEditDateTime(document.dateTime()),
                DocumentElementMapper.toEditor(document.owner()),
                DocumentElementMapper.toEditorList(document.editorList())
        );
    }

    public static DocumentModel toModel(DocumentEntity entity) {
        return new DocumentModel(
                entity.getId(),
                DocumentElementMapper.toTitleDto(entity.getTitle()),
                DocumentElementMapper.toDetailListDto(entity.getDetailList()),
                DocumentElementMapper.toContentListDto(entity.getContentList()),
                entity.getCategoryList(),
                entity.getViewCount(),
                entity.getVersion(),
                DocumentElementMapper.toTypeDto(entity.getType()),
                DocumentElementMapper.toEditDateTimeDto(entity.getDateTime()),
                DocumentElementMapper.toEditorDto(entity.getOwner()),
                DocumentElementMapper.toEditorListDto(entity.getEditorList())
        );
    }

//    public static FullDocumentResponse toFullResponse(DocumentEntity entity) {
//        return new FullDocumentResponse(
//                entity.getId(),
//                entity.getTitle(),
//                entity.getInfo().subTitle(),
//                entity.getInfo().detailList().stream()
//                        .map(detail -> new FullDocumentResponse.Detail(detail.title(), detail.content()))
//                        .toList(),
//                entity.getContentList().stream()
//                        .map(content -> new FullDocumentResponse.Content(content.index(), content.title(), content.content()))
//                        .toList(),
//                entity.getCategoryList(),
//                entity.getView(),
//                entity.getVersion(),
//                entity.getType().name(),
//                new FullDocumentResponse.EditDateTime(entity.getDateTime().getCreated().toString(),
//                        entity.getDateTime().getUpdated().toString()),
//                new FullDocumentResponse.Editor(entity.getOwner().name(), entity.getOwner().userId()),
//                entity.getEditorList().stream()
//                        .map(editor -> new FullDocumentResponse.Editor(editor.name(), editor.userId()))
//                        .toList()
//        );
//    }
}
