package com.thekliar.reactive.model;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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
