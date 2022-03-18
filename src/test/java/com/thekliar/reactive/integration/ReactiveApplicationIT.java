package com.thekliar.reactive.integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DirtiesContext
@Tag("docker")
public class ReactiveApplicationIT {
  private static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("mongo:latest");

  @Container
  static MongoDBContainer mongodb = new MongoDBContainer(DOCKER_IMAGE_NAME)
      .withExposedPorts(27017);

  @Autowired
  private WebTestClient client;

  @DynamicPropertySource
  public static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongodb::getReplicaSetUrl);
  }

  @Test
  void whenGetInvalidBlog_thenNotFound() {
    client.get()
        .uri("/blogs/1")
        .exchange()
        .expectStatus()
        .isNotFound();
  }
}
