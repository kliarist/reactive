package com.thekliar.reactive.service;

import com.thekliar.reactive.dto.BlogDTO;
import com.thekliar.reactive.mapper.BlogMapper;
import com.thekliar.reactive.model.Blog;
import com.thekliar.reactive.model.QBlog;
import com.thekliar.reactive.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BlogService {

  private final BlogRepository blogRepository;
  private final BlogMapper blogMapper;

  public Mono<BlogDTO> save(BlogDTO dto) {
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
        .flatMap(blogRepository::insert)
        .map(blogMapper::toDTO);
  }


  public Mono<BlogDTO> findById(String id) {
    return blogRepository.findOne(QBlog.blog.id.eq(id))
        .map(blogMapper::toDTO);
  }

  public Flux<BlogDTO> findAll() {
    return blogMapper.toDTOs(blogRepository.findAll());
  }

  public Mono<Void> deleteById(String id) {
    return blogRepository.deleteById(id);
  }
}
