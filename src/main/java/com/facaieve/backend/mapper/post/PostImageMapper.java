package com.facaieve.backend.mapper.post;

import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.image.PostImageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostImageMapper {
    S3ImageInfo postImageEntityToPostImageDto(PostImageEntity postImageEntity);
    PostImageEntity postImageDtoToPostImageEntity(S3ImageInfo s3ImageInfo);

}
