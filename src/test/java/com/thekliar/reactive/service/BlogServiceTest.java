package com.thekliar.reactive.service;

import static com.thekliar.reactive.utils.BlogUtils.createBlog;
import static com.thekliar.reactive.utils.BlogUtils.createBlogDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.thekliar.reactive.dto.BlogDTO;
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

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

  @InjectMocks
  BlogService service;
  @Mock
  BlogRepository blogRepository;
  @Mock
  BlogMapper blogMapper;

  private final String title = "BlogTitle";
  private final String content = "This is the content of the blog";
  private final String author = "author";

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

    BlogDTO dto1 = createBlogDTO(id1, title, content, author);
    BlogDTO dto2 = createBlogDTO(id1, title, content, author);
    List<BlogDTO> blogDTOs = List.of(dto1, dto2);
    Flux<BlogDTO> blogDTOFlux = Flux.fromIterable(blogDTOs);

    given(blogRepository.findAll()).willReturn(blogFlux);
    given(blogMapper.toDTOs(blogFlux)).willReturn(blogDTOFlux);

    Flux<BlogDTO> actual = service.findAll();
    actual.subscribe();

    assertEquals(blogDTOFlux, actual);

    then(blogRepository).should().findAll();
    then(blogMapper).should().toDTOs(blogFlux);
  }

  @Test
  void givenBlogId_whenGetBlogById_thenCorrectBlog() {

    String id = UUID.randomUUID().toString();
    Blog blog = createBlog(id, title, content, author);
    BlogDTO dto = createBlogDTO(id, title, content, author);
    BooleanExpression predicate = QBlog.blog.id.eq(id);

    given(blogRepository.findOne(predicate)).willReturn(Mono.just(blog));
    given(blogMapper.toDTO(blog)).willReturn(dto);

    StepVerifier.create(service.findById(id))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogRepository).should().findOne(predicate);
    then(blogMapper).should().toDTO(blog);
  }

  @Test
  void whenInsertNewBlog_thenBlogInserted() {
    String blogId = UUID.randomUUID().toString();

    BlogDTO dto = createBlogDTO(null, title, content, author);
    Blog blog = createBlog(blogId, title, content, author);

    given(blogMapper.toDocument(dto)).willReturn(blog);
    given(blogRepository.insert(blog)).willReturn(Mono.just(blog));
    given(blogMapper.toDTO(blog)).willReturn(dto);

    StepVerifier.create(service.save(dto))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogMapper).should().toDocument(dto);
    then(blogRepository).should().insert(any(Blog.class));
    then(blogMapper).should().toDTO(blog);
  }

  @Test
  void whenUpdateBlog_thenBlogUpdated() {
    String blogId = UUID.randomUUID().toString();

    BlogDTO dto = createBlogDTO(blogId, title, content, author);
    Blog blog = createBlog(blogId, title, content, author);

    given(blogRepository.findById(blogId)).willReturn(Mono.just(blog));
    willDoNothing().given(blogMapper).map(dto, blog);
    given(blogRepository.insert(blog)).willReturn(Mono.just(blog));
    given(blogMapper.toDTO(blog)).willReturn(dto);

    StepVerifier.create(service.save(dto))
        .assertNext(actual -> assertEquals(dto, actual))
        .verifyComplete();

    then(blogRepository).should().findById(blogId);
    then(blogMapper).should().map(dto, blog);
    then(blogRepository).should().insert(any(Blog.class));
    then(blogMapper).should().toDTO(blog);
  }
}
