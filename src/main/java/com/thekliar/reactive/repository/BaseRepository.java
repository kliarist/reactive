package com.thekliar.reactive.repository;

import com.thekliar.reactive.model.BaseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<D extends BaseDocument>
    extends ReactiveMongoRepository<D, String>,
    ReactiveQuerydslPredicateExecutor<D> {

}
