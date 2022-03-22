package com.thekliar.reactive.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "blog")
@QueryEntity
@Accessors(chain = true)
public class Blog extends BaseDocument {

  @TextIndexed
  private String title;

  private String content;

  @Indexed
  private String author;
}
