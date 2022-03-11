package com.thekliar.reactive.handler;

import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BlogHandler {

    private final BlogRepository blogRepository;

    public BlogHandler(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request
                .body(toMono(Blog.class))
                .doOnNext(blogRepository::save)
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
