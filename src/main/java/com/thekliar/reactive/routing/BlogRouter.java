package com.thekliar.reactive.routing;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.handler.BlogHandler;
import com.thekliar.reactive.service.BlogService;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Configuration
public class BlogRouter {

  @Bean
  @RouterOperations({
      @RouterOperation(
          path = "/blogs/", method = POST, produces = APPLICATION_JSON_VALUE,
          beanClass = BlogService.class, beanMethod = "save",
          operation = @Operation(
              operationId = "save",
              description = "Saves or updates a Blog. Will attempt to update when the id json value is present and exists in the DB.",
              responses = {
                  @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BlogDto.class))),
                  @ApiResponse(responseCode = "201", description = "Successful operation", content = @Content(schema = @Schema(implementation = BlogDto.class))),
                  @ApiResponse(responseCode = "400", description = "Invalid BlogDto supplied")},
              requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = BlogDto.class)))
          )),
      @RouterOperation(
          path = "/blogs/{id}", method = DELETE,
          beanClass = BlogService.class, beanMethod = "deleteById",
          operation = @Operation(
              operationId = "deleteById",
              description = "Deletes a Blog by its id.",
              responses = {
                  @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "Boolean")),
                  @ApiResponse(responseCode = "400", description = "Bad request")},
              parameters = {
                  @Parameter(in = ParameterIn.PATH, name = "id")}
          )),
      @RouterOperation(
          path = "/blogs/{id}", method = GET, produces = APPLICATION_JSON_VALUE,
          beanClass = BlogService.class, beanMethod = "findById",
          operation = @Operation(
              operationId = "findById",
              description = "Fetches a Blog by its id.",
              responses = {
                  @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BlogDto.class))),
                  @ApiResponse(responseCode = "400", description = "Bad request")},
              parameters = {
                  @Parameter(in = ParameterIn.PATH, name = "id")})
      ),
      @RouterOperation(
          path = "/blogs", method = GET, produces = APPLICATION_JSON_VALUE,
          beanClass = BlogService.class, beanMethod = "findAll",
          operation = @Operation(
              operationId = "findAll",
              description = "Fetches all Blogs.",
              responses = {
                  @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BlogDto.class))),
                  @ApiResponse(responseCode = "400", description = "Bad request")})
      )})
  public RouterFunction<ServerResponse> composedRoutes(final BlogHandler blogHandler) {

    return RouterFunctions.route()
        .path("/blogs", builder -> builder
            .POST("", blogHandler::save)
            .DELETE("/{id}", blogHandler::deleteById)
            .GET("/{id}", blogHandler::findById)
            .GET("", blogHandler::findAll))
        .build();
  }
}
