package com.thekliar.reactive.model.event;

import com.thekliar.reactive.dto.BlogDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BlogMessage extends BaseMessage {

  private BlogAction action;
  private BlogDto blogDto;

}
