package com.thekliar.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "blog")
public class Blog {

    @Id
    private String id;

    @TextIndexed
    private String title;

    private String content;

    @Indexed
    private String author;
}
