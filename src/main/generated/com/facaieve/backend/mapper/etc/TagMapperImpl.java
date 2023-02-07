package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.stubDate.TagStubData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagEntity tagStubDataToStubTagEntity(TagStubData tagStubData) {
        if ( tagStubData == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setTagId( tagStubData.getTagId() );
        tagEntity.setTagName( tagStubData.getTagName() );
        tagEntity.setDescription( tagStubData.getDescription() );

        return tagEntity;
    }

    @Override
    public TagEntity postTagDtoToTagEntity(TagDTO.PostTagDTO postTagDTO) {
        if ( postTagDTO == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setTagName( postTagDTO.getTagName() );
        tagEntity.setDescription( postTagDTO.getDescription() );

        return tagEntity;
    }

    @Override
    public TagEntity patchTagDtoToTagEntity(TagDTO.PatchTagDTO patchTagDTO) {
        if ( patchTagDTO == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setTagName( patchTagDTO.getTagName() );

        return tagEntity;
    }

    @Override
    public TagEntity getTagDtoToTagEntity(TagDTO.GetTagDTO getTagDTO) {
        if ( getTagDTO == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setTagId( getTagDTO.getTagId() );
        tagEntity.setTagName( getTagDTO.getTagName() );
        tagEntity.setDescription( getTagDTO.getDescription() );

        return tagEntity;
    }

    @Override
    public TagDTO.GetTagDTO tagEntityToGetTagEntity(TagEntity tagEntity) {
        if ( tagEntity == null ) {
            return null;
        }

        TagDTO.GetTagDTO getTagDTO = new TagDTO.GetTagDTO();

        getTagDTO.setTagId( tagEntity.getTagId() );
        getTagDTO.setTagName( tagEntity.getTagName() );
        getTagDTO.setDescription( tagEntity.getDescription() );

        return getTagDTO;
    }

    @Override
    public TagDTO.ResponseTagDTO tagEntityToResponseTagDTO(TagEntity tagEntity) {
        if ( tagEntity == null ) {
            return null;
        }

        TagDTO.ResponseTagDTO responseTagDTO = new TagDTO.ResponseTagDTO();

        responseTagDTO.setTagName( tagEntity.getTagName() );
        responseTagDTO.setDescription( tagEntity.getDescription() );

        return responseTagDTO;
    }
}
