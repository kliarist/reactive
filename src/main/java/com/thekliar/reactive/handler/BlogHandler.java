package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import java.net.URI;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.service.BlogService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BlogHandler {

  private final BlogService blogService;
  private final ValidationHandler validator;

  public @NotNull Mono<ServerResponse> save(ServerRequest request) {

    return request.bodyToMono(BlogDto.class).flatMap(dto -> {
      Errors errors = validator.validate(dto);
      if (errors.hasErrors()) {
        return onValidationErrors(errors);
      } else {
        return processBody(dto);
      }
    });
  }

  private Mono<ServerResponse> processBody(BlogDto dto) {
    boolean isUpdate = dto.getId() != null;

    return blogService.save(dto).flatMap(savedDto -> ServerResponse.status(isUpdate ? OK : CREATED)
        .location(URI.create("/blogs/" + savedDto.getId())).contentType(MediaType.APPLICATION_JSON)
        .bodyValue(savedDto));
  }

  private Mono<ServerResponse> onValidationErrors(Errors errors) {
    return ServerResponse.status(BAD_REQUEST).bodyValue(errors.getAllErrors());
  }

  public @NotNull Mono<ServerResponse> findById(ServerRequest request) {
    String id = request.pathVariable(ID);
    return blogService.findById(id)
        .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dto))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  @SuppressWarnings("unused")
  public @NotNull Mono<ServerResponse> findAll(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(blogService.findAll(), BlogDto.class);
  }

  public @NotNull Mono<ServerResponse> deleteById(ServerRequest request) {
    String id = request.pathVariable(ID);
    return ServerResponse.ok().body(blogService.deleteById(id), Void.class);
  }
}
