//package com.thekliar.reactive.service;
//
//import com.thekliar.reactive.model.Blog;
//import com.thekliar.reactive.repository.BlogRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Service
//@RequiredArgsConstructor
//public class BlogService {
//
//  private final BlogRepository blogRepository;
//
//  public Mono<Blog> createBlog(Blog blog) {
//    return blogRepository.insert(blog);
//  }
//
//  public Flux<Blog> findAll() {
//    return blogRepository.findAll();
//  }
//
//  public Mono<Blog> updateBlog(Blog blog, String id) {
//    return findOne(id).doOnSuccess(findBlog -> {
//      findBlog.setContent(blog.getContent());
//      findBlog.setTitle(blog.getTitle());
//      findBlog.setAuthor(blog.getAuthor());
//      blogRepository.save(findBlog).subscribe();
//    });
//  }
//
//  public Mono<Blog> findOne(String id) {
//    return blogRepository.findByIdAndDeleteIsFalse(id).
//        switchIfEmpty(Mono.error(new Exception("No Blog found with Id: " + id)));
//  }
//
//  public Flux<Blog> findByTitle(String title) {
//    return blogRepository.findByAuthorAndDeleteIsFalse(title)
//        .switchIfEmpty(Mono.error(new Exception("No Blog found with title Containing : " + title)));
//  }
//
//  public Mono<Boolean> delete(String id) {
//    return findOne(id).doOnSuccess(blog -> {
//      blog.setDelete(true);
//      blogRepository.save(blog).subscribe();
//    }).flatMap(blog -> Mono.just(Boolean.TRUE));
//  }
//}
