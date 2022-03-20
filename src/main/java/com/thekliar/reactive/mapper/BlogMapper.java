package com.thekliar.reactive.mapper;

import com.thekliar.reactive.dto.BlogDTO;
import com.thekliar.reactive.model.Blog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class BlogMapper extends BaseMapper<BlogDTO, Blog> {

}
