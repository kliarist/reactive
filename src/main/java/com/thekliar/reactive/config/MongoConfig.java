package com.thekliar.reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Mono;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.thekliar.reactive.repository")
@EnableReactiveMongoAuditing
public class MongoConfig {

  @Bean
  public ReactiveAuditorAware<String> auditorProvider() {
    return () -> Mono.just("system");
  }
}
