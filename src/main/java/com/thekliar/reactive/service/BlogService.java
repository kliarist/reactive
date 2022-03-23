package com.thekliar.reactive.service;

import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.mapper.BlogMapper;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.model.QBlog;
import com.thekliar.reactive.repository.BlogRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;
  private final BlogMapper blogMapper;

  public Mono<BlogDto> save(BlogDto dto) {
    Mono<Blog> monoBlog;
    if (dto.getId() != null) {
      monoBlog = blogRepository
          .findById(dto.getId())
          .map(blog -> {
            blogMapper.map(dto, blog);
            return blog;
          });
    } else {
      monoBlog = Mono.just(blogMapper.toDocument(dto));
    }
    return monoBlog
        .flatMap(blogRepository::save)
        .map(blogMapper::toDto);
  }


  public Mono<BlogDto> findById(String id) {
    return blogRepository.findOne(QBlog.blog.id.eq(id))
        .map(blogMapper::toDto);
  }

  public Flux<BlogDto> findAll() {
    return blogMapper.toDtos(blogRepository.findAll());
  }

  public Mono<Void> deleteById(String id) {
    return blogRepository.deleteById(id);
  }
}
