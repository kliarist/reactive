package com.thekliar.reactive.mapper;

import com.thekliar.reactive.dto.BaseDto;
import com.thekliar.reactive.model.BaseDocument;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import reactor.core.publisher.Flux;

public interface BaseMapper<D extends BaseDto, T extends BaseDocument> {

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  T toDocument(D dto);

  D toDto(T document);

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  void map(D dto, @MappingTarget T document);

  default Flux<D> toDtos(Flux<T> all) {
    return all.map(this::toDto);
  }

  @SuppressWarnings("unused")
  default Flux<T> toDocuments(Flux<D> all) {
    return all.map(this::toDocument);
  }

}
