package com.thekliar.reactive.dto;

import java.io.Serializable;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class BaseDto implements Serializable {

  private String id;

  @JsonProperty(access = Access.READ_ONLY)
  private Instant createdOn;

  @JsonProperty(access = Access.READ_ONLY)
  private String createdBy;

  @JsonProperty(access = Access.READ_ONLY)
  private Instant modifiedOn;

  @JsonProperty(access = Access.READ_ONLY)
  private String modifiedBy;

  @JsonProperty(access = Access.READ_ONLY)
  private String version;

}
