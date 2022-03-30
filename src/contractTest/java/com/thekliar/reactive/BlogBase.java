package com.thekliar.reactive;

import static com.thekliar.reactive.utils.BlogUtils.createBlogDto;
import static org.mockito.BDDMockito.given;
import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.handler.BlogHandler;
import com.thekliar.reactive.handler.ValidationHandler;
import com.thekliar.reactive.routing.BlogRouter;
import com.thekliar.reactive.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
@ContextConfiguration(classes = {BlogRouter.class, BlogHandler.class, ValidationHandler.class})
public class BlogBase {

  @Autowired
  RouterFunction<ServerResponse> routerFunction;
  @MockBean
  BlogService blogService;

  @BeforeEach
  void setUp() {
    BlogDto dto = createBlogDto("1", "title", "content", "author");
    given(blogService.findById("1")).willReturn(Mono.just(dto));
    RestAssuredWebTestClient.standaloneSetup(routerFunction);
  }
}
