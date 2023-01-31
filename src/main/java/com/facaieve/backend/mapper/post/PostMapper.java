package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;

public interface PostMapper {

    Object entityDtoToEntityStubData(Object entityStubData);

    Object postDtoToEntity(Object postDto);

    Object patchDtoToEntity(Object patchDto);

    Object getDtoToEntity(Object getDto);

    Object deleteDtoToEntity(Object deleteDto);

    Object entityToResponseDto(Object entity);

    Object entityIncludeURIToEntity(Object dtoWithUri);

    Object entityToResponseEntityIncludeURI(Object dtoWithUri);
}
