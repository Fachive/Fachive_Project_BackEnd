package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.stubDate.TagStubData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagEntity tagStubDataToStubTagEntity(TagStubData tagStubData);

    TagEntity postTagDtoToTagEntity(TagDTO.PostTagDTO postTagDTO);
    TagEntity patchTagDtoToTagEntity(TagDTO.PatchTagDTO patchTagDTO);
    TagEntity getTagDtoToTagEntity(TagDTO.GetTagDTO getTagDTO);
    TagDTO.GetTagDTO tagEntityToGetTagEntity(TagEntity tagEntity);
    TagDTO.ResponseTagDTO tagEntityToResponseTagDTO(TagEntity tagEntity);


}
