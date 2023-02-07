package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PostImageMapperImpl implements PostImageMapper {

    @Override
    public PostImageDto postImageEntityToPostImageDto(PostImageEntity postImageEntity) {
        if ( postImageEntity == null ) {
            return null;
        }

        PostImageDto.PostImageDtoBuilder postImageDto = PostImageDto.builder();

        postImageDto.fileName( postImageEntity.getFileName() );
        postImageDto.fileURI( postImageEntity.getFileURI() );

        return postImageDto.build();
    }

    @Override
    public PostImageEntity postImageDtoToPostImageEntity(PostImageDto postImageDto) {
        if ( postImageDto == null ) {
            return null;
        }

        PostImageEntity.PostImageEntityBuilder postImageEntity = PostImageEntity.builder();

        postImageEntity.fileName( postImageDto.getFileName() );
        postImageEntity.fileURI( postImageDto.getFileURI() );

        return postImageEntity.build();
    }
}
