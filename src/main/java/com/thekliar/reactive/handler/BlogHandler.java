package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.model.QBlog;
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
//        .flatMap(blogRepository::save)
        .doOnNext(blogRepository::insert)
        .then(ok().build());
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    String id = request.pathVariable(ID);
    return ok().body(blogRepository.findOne(QBlog.blog.id.eq(id)), Blog.class);
  }

  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ok().body(blogRepository.findAll(), Blog.class);
  }
}
