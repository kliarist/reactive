package com.thekliar.reactive.mapper;

import com.thekliar.reactive.dto.BaseDto;
import com.thekliar.reactive.model.BaseDocument;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import reactor.core.publisher.Flux;

public abstract class BaseMapper<D extends BaseDto, T extends BaseDocument> {

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  public abstract T toDocument(D dto);

  public abstract D toDto(T document);

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  public abstract void map(D dto, @MappingTarget T document);

  public Flux<D> toDtos(Flux<T> all) {
    return all.map(this::toDto);
  }

  @SuppressWarnings("unused")
  public Flux<T> toDocuments(Flux<D> all) {
    return all.map(this::toDocument);
  }

}
