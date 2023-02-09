package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses ={
        PostImageMapper.class,
        CategoryMapper.class,
        TagMapper.class
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

    //개별 게시글 페이지를 위한 객체 반환
    default FashionPickupDto.ResponseFashionPickupDtoForEntity fashionPickupEntityToResponseFashionPickupDto(FashionPickupEntity fashionPickupEntity){

        return FashionPickupDto.ResponseFashionPickupDtoForEntity
                .builder()
                .fashionPickupEntityId(fashionPickupEntity.getFashionPickupEntityId())
                .title(fashionPickupEntity.getTitle())
                .body(fashionPickupEntity.getBody())
                .views(fashionPickupEntity.getViews())
                .myPicks(fashionPickupEntity.getMyPick().size())
                .tagList(fashionPickupEntity.getTagEntities()
                        .stream().map(tagEntity -> new TagDTO.ResponseTagDTO(tagEntity.getTagEntity().getTagName()))
                        .collect(Collectors.toList()))
                .s3ImageUriList(fashionPickupEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileURI)
                        .collect(Collectors.toList()))
                .build();
    }

    //메인페이지, 게시글 목록 페이지를 위한 게시글 객체 하나 반환
    default FashionPickupDto.ResponseFashionPickupDtoForEntities fashionPickupEntityForMainList(FashionPickupEntity fashionPickupEntity){

        return FashionPickupDto.ResponseFashionPickupDtoForEntities
                .builder()
                .title(fashionPickupEntity.getTitle())
                .body(fashionPickupEntity.getBody())
                .views(fashionPickupEntity.getViews())
                .myPicks(fashionPickupEntity.getMyPick().size())
                .tagList(fashionPickupEntity.getTagEntities()
                        .stream().map(tagEntity -> new TagDTO.ResponseTagDTO(tagEntity.getTagEntity().getTagName()))
                        .collect(Collectors.toList()))
                .thumpNailImageUri(fashionPickupEntity.getS3ImgInfo().get(0).getFileURI())
                .build();
    }

//    @Mapping(source = "postImageEntities", target = "postImageDtoList")
//    @Mapping(source = "categoryEntity", target = "responseCategoryDTO")
//    @Mapping(source = "tagEntities", target = "responseTagDTOList")
//    FashionPickupDto.ResponseFashionPickupIncludeURI
//    fashionPickupEntityToResponseFashionPickupIncludeURI(FashionPickupEntity fashionPickupEntity);




}
