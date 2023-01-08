package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.MyPickDTO;
import com.facaieve.backend.entity.etc.MyPickEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MyPickMapper {

    MyPickEntity  responseMyPickDtoToMyPickEntity(MyPickDTO.ResponseMyPickDTO responseMyPickDTO);
    MyPickEntity  postMyPickDtoToMyPickEntity(MyPickDTO.PostMyPickDTO postMyPickDTO);
    MyPickDTO.ResponseMyPickDTO myPickEntityToResponseMyPickDTO(MyPickEntity myPickEntity);


}
