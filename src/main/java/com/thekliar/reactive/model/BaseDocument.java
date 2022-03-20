package com.thekliar.reactive.model;

import com.querydsl.core.annotations.QuerySupertype;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@QuerySupertype
@Accessors(chain = true)
public class BaseDocument {

  @Id
  private String id;

  @CreatedDate
  private Instant createdOn;

  @LastModifiedDate
  private Instant modifiedOn;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

  @Version
  private String version;

}
