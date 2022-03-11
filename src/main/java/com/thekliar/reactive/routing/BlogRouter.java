package com.thekliar.reactive.routing;

import com.thekliar.reactive.handler.BlogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BlogRouter {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(final BlogHandler blogHandler) {

        return RouterFunctions.route()
                .path("/blogs", builder -> builder
                        .POST("", blogHandler::save)
                        .GET("/{id}", blogHandler::findById)
                        .GET("", blogHandler::findAll))
                .build();
    }
}
