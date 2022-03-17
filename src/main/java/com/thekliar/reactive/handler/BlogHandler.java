package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.model.QBlog;
import com.thekliar.reactive.repository.BlogRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import lombok.RequiredArgsConstructor;
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
    String id = request.pathVariable(ID);
    Mono<Blog> monoBlog = blogRepository.findOne(QBlog.blog.id.eq(id));
    return ok().body(monoBlog, Blog.class);
  }

  @SuppressWarnings("unused")
  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ok().body(blogRepository.findAll(), Blog.class);
  }

  public Mono<ServerResponse> deleteById(ServerRequest request) {
    String id = request.pathVariable(ID);
    Mono<Void> monoVoid = blogRepository.deleteById(id);
    return ok().body(monoVoid, Blog.class);
  }
}
