package com.thekliar.reactive.mapper;

import com.thekliar.reactive.dto.BaseDTO;
import com.thekliar.reactive.model.BaseDocument;
import com.thekliar.reactive.repository.BaseRepository;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public abstract class BaseMapper<D extends BaseDTO, T extends BaseDocument> {

  @Autowired
  BaseRepository<T> repository;

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  public abstract T toDocument(D dto);

  public abstract D toDTO(T document);

  @Mapping(ignore = true, target = "modifiedBy")
  @Mapping(ignore = true, target = "modifiedOn")
  @Mapping(ignore = true, target = "createdBy")
  @Mapping(ignore = true, target = "createdOn")
  @Mapping(ignore = true, target = "version")
  public abstract void map(D dto, @MappingTarget T document);

  public Flux<D> toDTOs(Flux<T> all) {
    return all.map(this::toDTO);
  }

  @SuppressWarnings("unused")
  public Flux<T> toDocuments(Flux<D> all) {
    return all.map(this::toDocument);
  }

}
