package com.thekliar.reactive.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Reactive REST API demo",
        description = "This is the Reactive REST API documentation",
        version = "1.0.0"))
public class OpenApiConfig {
}
