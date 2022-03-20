package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.thekliar.reactive.dto.BlogDTO;
import com.thekliar.reactive.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BlogHandler {

  private final BlogService blogService;

  public Mono<ServerResponse> save(ServerRequest request) {

    boolean[] isUpdate = new boolean[1];
    return request
        .bodyToMono(BlogDTO.class)
        .doOnNext(blogDTO -> isUpdate[0] = blogDTO.getId() != null)
        .flatMap(blogService::save)
        .flatMap(dto -> ServerResponse
            .status(isUpdate[0] ? OK : CREATED)
            .location(URI.create("/blogs/" + dto.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dto)
        );
  }

  public Mono<ServerResponse> findById(ServerRequest request) {
    String id = request.pathVariable(ID);
    return blogService
        .findById(id)
        .flatMap(dto -> ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(dto)
        )
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  @SuppressWarnings("unused")
  public Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(blogService.findAll(), BlogDTO.class);
  }

  public Mono<ServerResponse> deleteById(ServerRequest request) {
    String id = request.pathVariable(ID);
    return ServerResponse.ok().body(blogService.deleteById(id), Void.class);
  }
}
