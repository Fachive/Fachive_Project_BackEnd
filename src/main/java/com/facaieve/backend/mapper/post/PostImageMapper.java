package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostImageMapper {
    PostImageDto postImageEntityToPostImageDto(PostImageEntity postImageEntity);
    PostImageEntity postImageDtoToPostImageEntity(PostImageDto postImageDto);

}
