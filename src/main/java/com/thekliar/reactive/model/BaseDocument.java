package com.thekliar.reactive.model;

import java.time.Instant;
import com.querydsl.core.annotations.QuerySupertype;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
