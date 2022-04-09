package com.thekliar.reactive.handler;

import static com.thekliar.reactive.config.AppConstants.ID;
import static com.thekliar.reactive.model.event.BlogAction.AMEND;
import static com.thekliar.reactive.model.event.BlogAction.DELETE;
import static com.thekliar.reactive.model.event.BlogAction.NEW;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import java.net.URI;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.model.event.BlogAction;
import com.thekliar.reactive.model.event.BlogMessage;
import com.thekliar.reactive.service.BlogService;
import com.thekliar.reactive.service.kafka.ReactiveKafkaProducerService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
  private final ReactiveKafkaProducerService kafkaProducerService;
  private final ValidationHandler validator;
  @Value("${kafka.topic.blog-events}")
  private String topic;

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
    return blogService.findById(id)
        .flatMap(dto -> ServerResponse
            .ok()
            .body(blogService.deleteById(id)
                .doFinally(signalType -> sendKafkaMessage(DELETE, dto)), Void.class))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  private Mono<ServerResponse> processBody(BlogDto dto) {
    boolean isUpdate = dto.getId() != null;

    return blogService.save(dto)
        .doOnNext(blogDto -> sendKafkaMessage(isUpdate ? AMEND : NEW, blogDto))
        .flatMap(savedDto -> ServerResponse
            .status(isUpdate ? OK : CREATED)
            .location(URI.create("/blogs/" + savedDto.getId()))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(savedDto));
  }

  private Mono<ServerResponse> onValidationErrors(Errors errors) {
    return ServerResponse.status(BAD_REQUEST).bodyValue(errors.getAllErrors());
  }

  private void sendKafkaMessage(BlogAction action, BlogDto blogDto) {
    BlogMessage message = new BlogMessage(action, blogDto);
    kafkaProducerService.sendMessage(topic, message);
  }
}
