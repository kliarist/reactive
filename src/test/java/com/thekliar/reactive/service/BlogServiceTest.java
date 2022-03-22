package com.thekliar.reactive.service;

import static com.thekliar.reactive.utils.BlogUtils.createBlog;
import static com.thekliar.reactive.utils.BlogUtils.createBlogDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import java.util.List;
import java.util.UUID;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.mapper.BlogMapper;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.model.QBlog;
import com.thekliar.reactive.repository.BlogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

  private final String title = "BlogTitle";
  private final String content = "This is the content of the blog";
  private final String author = "author";
  @InjectMocks
  BlogService service;
  @Mock
  BlogRepository blogRepository;
  @Mock
  BlogMapper blogMapper;

  @AfterEach
  void tearDown() {
    then(blogRepository).shouldHaveNoMoreInteractions();
    then(blogMapper).shouldHaveNoMoreInteractions();
  }

  @Test
  void whenDeleteBlog_thenBlogDeleted() {
    String id = UUID.randomUUID().toString();
    given(blogRepository.deleteById(id)).willReturn(Mono.empty());
    service.deleteById(id).subscribe();
    then(blogRepository).should().deleteById(id);
  }

  @Test
  void whenFindAllBlogs_thenCorrectBlogs() {

    String id1 = UUID.randomUUID().toString();
    String id2 = UUID.randomUUID().toString();

    Blog blog1 = createBlog(id1, title, content, author);
    Blog blog2 = createBlog(id2, title, content, author);
    List<Blog> blogs = List.of(blog1, blog2);
    Flux<Blog> blogFlux = Flux.fromIterable(blogs);

    BlogDto dto1 = createBlogDto(id1, title, content, author);
    BlogDto dto2 = createBlogDto(id1, title, content, author);
    List<BlogDto> blogDtos = List.of(dto1, dto2);
    Flux<BlogDto> blogDtoFlux = Flux.fromIterable(blogDtos);

    given(blogRepository.findAll()).willReturn(blogFlux);
    given(blogMapper.toDtos(blogFlux)).willReturn(blogDtoFlux);

    Flux<BlogDto> actual = service.findAll();
    actual.subscribe();

    assertEquals(blogDtoFlux, actual);

    then(blogRepository).should().findAll();
    then(blogMapper).should().toDtos(blogFlux);
  }

  @Test
  void givenBlogId_whenGetBlogById_thenCorrectBlog() {

    String id = UUID.randomUUID().toString();
    Blog blog = createBlog(id, title, content, author);
    BlogDto dto = createBlogDto(id, title, content, author);
    BooleanExpression predicate = QBlog.blog.id.eq(id);

    given(blogRepository.findOne(predicate)).willReturn(Mono.just(blog));
    given(blogMapper.toDto(blog)).willReturn(dto);

    StepVerifier.create(service.findById(id))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogRepository).should().findOne(predicate);
    then(blogMapper).should().toDto(blog);
  }

  @Test
  void whenInsertNewBlog_thenBlogInserted() {
    String blogId = UUID.randomUUID().toString();

    BlogDto dto = createBlogDto(null, title, content, author);
    Blog blog = createBlog(blogId, title, content, author);

    given(blogMapper.toDocument(dto)).willReturn(blog);
    given(blogRepository.save(blog)).willReturn(Mono.just(blog));
    given(blogMapper.toDto(blog)).willReturn(dto);

    StepVerifier.create(service.save(dto))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogMapper).should().toDocument(dto);
    then(blogRepository).should().save(any(Blog.class));
    then(blogMapper).should().toDto(blog);
  }

  @Test
  void whenUpdateBlog_thenBlogUpdated() {
    String blogId = UUID.randomUUID().toString();

    BlogDto dto = createBlogDto(blogId, title, content, author);
    Blog blog = createBlog(blogId, title, content, author);

    given(blogRepository.findById(blogId)).willReturn(Mono.just(blog));
    willDoNothing().given(blogMapper).map(dto, blog);
    given(blogRepository.save(blog)).willReturn(Mono.just(blog));
    given(blogMapper.toDto(blog)).willReturn(dto);

    StepVerifier.create(service.save(dto))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogRepository).should().findById(blogId);
    then(blogMapper).should().map(dto, blog);
    then(blogRepository).should().save(any(Blog.class));
    then(blogMapper).should().toDto(blog);
  }
}
