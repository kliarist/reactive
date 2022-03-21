package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.thekliar.reactive.dto.BlogDTO;
import com.thekliar.reactive.service.BlogService;
import com.thekliar.reactive.validator.ValidationHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BlogHandler extends ValidationHandler<BlogDTO> {

  private final BlogService blogService;

  public Mono<ServerResponse> save(ServerRequest request) {

    return request.bodyToMono(BlogDTO.class).flatMap(dto -> {
      Errors errors = this.validate(dto);
      if (errors.hasErrors()) {
        return onValidationErrors(errors);
      } else {
        return processBody(dto);
      }
    });
  }

  private Mono<ServerResponse> processBody(BlogDTO dto) {
    boolean isUpdate = dto.getId() != null;

    return blogService.save(dto)
        .flatMap(savedDTO -> ServerResponse
            .status(isUpdate ? OK : CREATED)
            .location(URI.create("/blogs/" + savedDTO.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(savedDTO)
        );
  }

  private Mono<ServerResponse> onValidationErrors(Errors errors) {
    return ServerResponse.status(BAD_REQUEST).bodyValue(errors.getAllErrors());
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
