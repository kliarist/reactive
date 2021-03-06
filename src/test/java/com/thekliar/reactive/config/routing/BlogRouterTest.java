package com.thekliar.reactive.config.routing;

import static com.thekliar.reactive.utils.BlogUtils.createBlogDto;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import java.util.List;
import java.util.UUID;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.handler.BlogHandler;
import com.thekliar.reactive.handler.ValidationHandler;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.routing.BlogRouter;
import com.thekliar.reactive.service.BlogService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@ContextConfiguration(classes = {BlogRouter.class, BlogHandler.class, ValidationHandler.class})
class BlogRouterTest {

  private final String title = "BlogTitle";
  private final String content = "This is the content of the blog";
  private final String author = "author";
  @Autowired
  ApplicationContext context;
  @MockBean
  BlogService blogService;
  WebTestClient client;

  @BeforeEach
  void setUp() {
    client = WebTestClient.bindToApplicationContext(context).build();
  }

  @AfterEach
  void tearDown() {
    then(blogService).shouldHaveNoMoreInteractions();
  }

  @Test
  void givenInvalidBlogId_whenGetBlogById_thenNotFound() {
    String id = UUID.randomUUID().toString();

    given(blogService.findById(id)).willReturn(Mono.empty());

    client.get()
        .uri("/blogs/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();

    then(blogService).should().findById(id);
  }

  @Test
  void givenBlogId_whenGetBlogById_thenCorrectBlog() {
    String id = UUID.randomUUID().toString();
    BlogDto dto = createBlogDto(id, title, content, author);

    given(blogService.findById(id)).willReturn(Mono.just(dto));

    client.get()
        .uri("/blogs/{id}", id)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(BlogDto.class)
        .isEqualTo(dto);

    then(blogService).should().findById(id);
  }

  @Test
  void whenGetAllBlogs_thenCorrectBlogs() {
    String id = UUID.randomUUID().toString();

    BlogDto dto1 = createBlogDto(id, title, content, author);
    BlogDto dto2 = createBlogDto(id, title, content, author);
    List<BlogDto> dtos = List.of(dto1, dto2);
    Flux<BlogDto> dtoFlux = Flux.fromIterable(dtos);

    given(blogService.findAll()).willReturn(dtoFlux);

    client.get()
        .uri("/blogs")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(BlogDto.class)
        .isEqualTo(dtos);

    then(blogService).should().findAll();
  }

  @Test
  void whenInsertNewBlog_thenBlogInserted() {
    String id = UUID.randomUUID().toString();
    BlogDto dto = createBlogDto(null, title, content, author);
    BlogDto dtoSaved = createBlogDto(id, title, content, author);

    given(blogService.save(dto)).willReturn(Mono.just(dtoSaved));

    client.post()
        .uri("/blogs")
        .body(Mono.just(dto), Blog.class)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader().location("/blogs/" + id);

    then(blogService).should().save(dto);
  }

  @Test
  void whenUpdateBlog_thenBlogUpdated() {
    String id = UUID.randomUUID().toString();

    BlogDto dto = createBlogDto(id, title, content, author);

    given(blogService.save(dto)).willReturn(Mono.just(dto));

    client.post()
        .uri("/blogs")
        .body(Mono.just(dto), Blog.class)
        .exchange()
        .expectStatus()
        .isOk()
        .expectHeader().location("/blogs/" + id);

    then(blogService).should().save(dto);
  }

  @Test
  void whenDeleteBlog_thenBlogDeleted() {
    String id = UUID.randomUUID().toString();

    given(blogService.deleteById(id)).willReturn(Mono.empty());

    client.delete()
        .uri("/blogs/" + id)
        .exchange()
        .expectStatus()
        .isOk();

    then(blogService).should().deleteById(id);
  }

  @Test
  void whenInsertNewBlog_thenValidationErrors() {
    BlogDto dto = createBlogDto(null, null, null, null);

    client.post()
        .uri("/blogs")
        .body(Mono.just(dto), Blog.class)
        .exchange()
        .expectStatus()
        .isBadRequest();

  }

}
