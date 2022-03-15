package com.thekliar.reactive.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BlogHandler {

  private final BlogRepository blogRepository;

  public Mono<ServerResponse> save(ServerRequest request) {
    return request
        .bodyToMono(Blog.class)
        .flatMap(blogRepository::insert)
        .then(ok().build());
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    String id = request.pathVariable("id");
    return ok().body(blogRepository.findById(id), Blog.class);
  }

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ok().body(blogRepository.findAll(), Blog.class);
  }
}
