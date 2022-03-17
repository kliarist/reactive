package com.thekliar.reactive.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "blog")
@QueryEntity
public class Blog {

  @Id
  private String id;

  @TextIndexed
  private String title;

  private String content;

  @Indexed
  private String author;
}
