package com.thekliar.reactive.config.routing;

import com.thekliar.reactive.handler.BlogHandler;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.repository.BlogRepository;
import com.thekliar.reactive.routing.BlogRouter;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebFluxTest
@ContextConfiguration(classes = {BlogRouter.class, BlogHandler.class})
class BlogRouterTest {

    @Autowired
    ApplicationContext context;

    @MockBean
    BlogRepository blogRepository;

    WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void givenBlogId_whenGetBlogById_thenCorrectBlog() {

        Blog blog = Blog.builder().id("1").title("Blog1Title").content("This is the content of the blog").author("theo").build();

        given(blogRepository.findById("1")).willReturn(Mono.just(blog));

        client.get()
                .uri("/blogs/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Blog.class)
                .isEqualTo(blog);
    }

    @Test
    void whenGetAllBlogs_thenCorrectBlogs() {
        List<Blog> employees = List.of(
                Blog.builder().id("1").title("Blog1Title").content("This is the content of the blog1").author("theo").build(),
                Blog.builder().id("2").title("Blog2Title").content("This is the content of the blog2").author("theo").build());

        Flux<Blog> employeeFlux = Flux.fromIterable(employees);
        given(blogRepository.findAll()).willReturn(employeeFlux);

        client.get()
                .uri("/blogs")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Blog.class)
                .isEqualTo(employees);
    }

    @Test
    void whenUpdateBlog_thenBlogUpdated() {
        Blog blog = Blog.builder().id("1").title("Blog1Title").content("This is the content of the blog").author("theo").build();

        client.post()
                .uri("/blogs")
                .body(Mono.just(blog), Blog.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(blogRepository).save(blog);
    }

}