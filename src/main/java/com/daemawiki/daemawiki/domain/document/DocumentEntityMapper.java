package com.daemawiki.daemawiki.domain.document;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = DocumentElementMapper.class)
public interface DocumentEntityMapper {

    DocumentEntity toEntity(DocumentModel document);

    DocumentModel toModel(DocumentEntity entity);

    default Mono<DocumentModel> toMonoModel(Mono<DocumentEntity> mono) {
        return mono.map(this::toModel);
    }

    default Flux<DocumentModel> toFluxModel(Flux<DocumentEntity> flux) {
        return flux.map(this::toModel);
    }
}
