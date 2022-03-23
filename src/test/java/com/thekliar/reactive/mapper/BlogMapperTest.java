package com.thekliar.reactive.mapper;

import static com.thekliar.reactive.utils.BlogUtils.createBlog;
import static com.thekliar.reactive.utils.BlogUtils.createBlogDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.util.UUID;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.model.Blog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BlogMapperTest {

  BlogMapper mapper = new BlogMapperImpl();
  String id = UUID.randomUUID().toString();
  private final String title = "title";
  private final String content = "content";
  private final String author = "author";
  private final Instant now = Instant.now();
  private final String auditUser = "system";
  private final String version = "0";

  @Test
  void whenMapToDocument_thenDocumentMatches() {
    BlogDto blogDto = createBlogDto(id, title, content, author, now, now, auditUser, auditUser, version);
    Blog expected = createBlog(id, title, content, author);

    Blog actual = mapper.toDocument(blogDto);

    assertEquals(expected, actual);
  }

  @Test
  void whenMap_thenMatches() {
    BlogDto blogDto = createBlogDto(id, title, content, author, now, now, auditUser, auditUser, version);
    Blog actual = createBlog(null, null, null, null);
    Blog expected = createBlog(id, title, content, author);

    mapper.map(blogDto, actual);

    assertEquals(expected, actual);
  }

  @Test
  void whenMapToFluxOfDocument_thenFluxMatches() {
    BlogDto blogDto = createBlogDto(id, title, content, author);
    Blog expected = createBlog(id, title, content, author);

    StepVerifier
        .create(mapper.toDocuments(Flux.just(blogDto)))
        .assertNext(actual -> assertEquals(expected, actual))
        .verifyComplete();

  }

  @Test
  void whenMapToDto_thenDtoMatches() {
    Blog blog = createBlog(id, title, content, author, now, now, auditUser, auditUser, version);
    BlogDto expected = createBlogDto(id, title, content, author, now, now, auditUser, auditUser, version);

    BlogDto actual = mapper.toDto(blog);

    assertEquals(expected, actual);
  }

  @Test
  void whenMapToFluxOfDtos_thenFluxMatches() {
    Blog blog = createBlog(id, title, content, author);
    BlogDto expected = createBlogDto(id, title, content, author);

    StepVerifier
        .create(mapper.toDtos(Flux.just(blog)))
        .assertNext(actual -> assertEquals(expected, actual))
        .verifyComplete();
  }

}
