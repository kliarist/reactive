package com.thekliar.reactive.mapper;

import com.thekliar.reactive.dto.BlogDto;
import com.thekliar.reactive.model.Blog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlogMapper extends BaseMapper<BlogDto, Blog> {

}
