package com.thekliar.reactive.utils;

import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.model.Blog;

public final class BlogUtils {

  public static Blog createBlog(String id, String title, String content, String author) {
    Blog blog = new Blog();
    blog.setTitle(title).setContent(content).setAuthor(author).setId(id);
    return blog;
  }

  public static BlogDto createBlogDto(String id, String title, String content, String author) {
    BlogDto blogDto = new BlogDto();
    blogDto.setTitle(title).setContent(content).setAuthor(author).setId(id);
    return blogDto;
  }

}
