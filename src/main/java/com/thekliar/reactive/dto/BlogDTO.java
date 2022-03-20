package com.thekliar.reactive.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BlogDTO extends BaseDTO {

  @NotEmpty
  private String title;
  @NotEmpty
  private String content;
  @NotEmpty
  private String author;
}
