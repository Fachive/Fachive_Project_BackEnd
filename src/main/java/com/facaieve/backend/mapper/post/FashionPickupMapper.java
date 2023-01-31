package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses ={
        PostImageMapper.class,
        CategoryMapper.class
})
public interface FashionPickupMapper {
    // 패션픽업 스텁데이터 -> 엔티티로 변환

    FashionPickupEntity fashionPickupDtoToFashionPickupStubData(FashionPuckupStubData fashionPuckupStubData);

    //postDto -> Entity
    FashionPickupEntity fashionPickupPostDtoToFashionPickupEntity(FashionPickupDto.PostFashionPickupDto postFashionPickupDto);

    //patchDto -> Entity
    FashionPickupEntity fashionPickupPatchDtoToFashionPickupEntity(FashionPickupDto.PatchFashionPickupDto patchFashionPickupDto);

    //getDto -> Entity
    FashionPickupEntity fashionPickupGetDtoToFashionPickupEntity(FashionPickupDto.GetFashionPickupDto getFashionPickupDto);

    //deleteDto ->Entity
    FashionPickupEntity fashionPickupDeleteDtoToFashionPickupEntity(FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto);

    FashionPickupDto.ResponseFashionPickupDto fashionPickupEntityToResponseFashionPickupEntity(FashionPickupEntity fashionPickupEntity);


    @Mapping(source = "postImageDtoList", target = "postImageEntities")
    FashionPickupEntity fashionPickupIncludeURIToFashionPickupEntity(
            FashionPickupDto.ResponseFashionPickupIncludeURI responseFashionPickupIncludeURI);

    @Mapping(source = "postImageEntities", target = "postImageDtoList")
    @Mapping(source = "categoryEntity", target = "responseCategoryDTO")
    FashionPickupDto.ResponseFashionPickupIncludeURI
    fashionPickupEntityToResponseFashionPickupIncludeURI(FashionPickupEntity fashionPickupEntity);




}
