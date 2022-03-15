package com.thekliar.reactive.repository;

import com.thekliar.reactive.model.Blog;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends ReactiveMongoRepository<Blog, String>,
    ReactiveQuerydslPredicateExecutor<Blog> {
}
