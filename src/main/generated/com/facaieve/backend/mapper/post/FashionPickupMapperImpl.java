package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.stubDate.FashionPuckupStubData;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class FashionPickupMapperImpl implements FashionPickupMapper {

    @Autowired
    private PostImageMapper postImageMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public FashionPickupEntity fashionPickupDtoToFashionPickupStubData(FashionPuckupStubData fashionPuckupStubData) {
        if ( fashionPuckupStubData == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setRegTime( fashionPuckupStubData.getRegTime() );
        fashionPickupEntity.setUpdateTime( fashionPuckupStubData.getUpdateTime() );
        fashionPickupEntity.setFashionPickupEntityId( fashionPuckupStubData.getFashionPickupEntityId() );
        fashionPickupEntity.setTitle( fashionPuckupStubData.getTitle() );
        fashionPickupEntity.setBody( fashionPuckupStubData.getBody() );
        fashionPickupEntity.setViews( fashionPuckupStubData.getViews() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupEntity fashionPickupPostDtoToFashionPickupEntity(FashionPickupDto.PostFashionPickupDto postFashionPickupDto) {
        if ( postFashionPickupDto == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setTitle( postFashionPickupDto.getTitle() );
        fashionPickupEntity.setBody( postFashionPickupDto.getBody() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupEntity fashionPickupPatchDtoToFashionPickupEntity(FashionPickupDto.PatchFashionPickupDto patchFashionPickupDto) {
        if ( patchFashionPickupDto == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setFashionPickupEntityId( patchFashionPickupDto.getFashionPickupEntityId() );
        fashionPickupEntity.setTitle( patchFashionPickupDto.getTitle() );
        fashionPickupEntity.setBody( patchFashionPickupDto.getBody() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupEntity fashionPickupGetDtoToFashionPickupEntity(FashionPickupDto.GetFashionPickupDto getFashionPickupDto) {
        if ( getFashionPickupDto == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setFashionPickupEntityId( getFashionPickupDto.getFashionPickupEntityId() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupEntity fashionPickupDeleteDtoToFashionPickupEntity(FashionPickupDto.DeleteFashionPickupDto deleteFashionPickupDto) {
        if ( deleteFashionPickupDto == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setFashionPickupEntityId( deleteFashionPickupDto.getFashionPickupEntityId() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupDto.ResponseFashionPickupDto fashionPickupEntityToResponseFashionPickupEntity(FashionPickupEntity fashionPickupEntity) {
        if ( fashionPickupEntity == null ) {
            return null;
        }

        FashionPickupDto.ResponseFashionPickupDto responseFashionPickupDto = new FashionPickupDto.ResponseFashionPickupDto();

        responseFashionPickupDto.setFashionPickupEntityId( fashionPickupEntity.getFashionPickupEntityId() );
        responseFashionPickupDto.setTitle( fashionPickupEntity.getTitle() );
        responseFashionPickupDto.setBody( fashionPickupEntity.getBody() );
        responseFashionPickupDto.setViews( fashionPickupEntity.getViews() );

        return responseFashionPickupDto;
    }

    @Override
    public FashionPickupEntity fashionPickupIncludeURIToFashionPickupEntity(FashionPickupDto.ResponseFashionPickupIncludeURI responseFashionPickupIncludeURI) {
        if ( responseFashionPickupIncludeURI == null ) {
            return null;
        }

        FashionPickupEntity fashionPickupEntity = new FashionPickupEntity();

        fashionPickupEntity.setPostImageEntities( postImageDtoListToPostImageEntityList( responseFashionPickupIncludeURI.getPostImageDtoList() ) );
        fashionPickupEntity.setFashionPickupEntityId( responseFashionPickupIncludeURI.getFashionPickupEntityId() );
        fashionPickupEntity.setTitle( responseFashionPickupIncludeURI.getTitle() );
        fashionPickupEntity.setBody( responseFashionPickupIncludeURI.getBody() );
        fashionPickupEntity.setViews( responseFashionPickupIncludeURI.getViews() );
        fashionPickupEntity.setMyPicks( responseFashionPickupIncludeURI.getMyPicks() );

        return fashionPickupEntity;
    }

    @Override
    public FashionPickupDto.ResponseFashionPickupIncludeURI fashionPickupEntityToResponseFashionPickupIncludeURI(FashionPickupEntity fashionPickupEntity) {
        if ( fashionPickupEntity == null ) {
            return null;
        }

        FashionPickupDto.ResponseFashionPickupIncludeURI.ResponseFashionPickupIncludeURIBuilder responseFashionPickupIncludeURI = FashionPickupDto.ResponseFashionPickupIncludeURI.builder();

        responseFashionPickupIncludeURI.postImageDtoList( postImageEntityListToPostImageDtoList( fashionPickupEntity.getPostImageEntities() ) );
        responseFashionPickupIncludeURI.responseCategoryDTO( categoryMapper.categoryEntityToResponseCategoryDto( fashionPickupEntity.getCategoryEntity() ) );
        responseFashionPickupIncludeURI.fashionPickupEntityId( fashionPickupEntity.getFashionPickupEntityId() );
        responseFashionPickupIncludeURI.title( fashionPickupEntity.getTitle() );
        responseFashionPickupIncludeURI.body( fashionPickupEntity.getBody() );
        responseFashionPickupIncludeURI.views( fashionPickupEntity.getViews() );
        responseFashionPickupIncludeURI.myPicks( fashionPickupEntity.getMyPicks() );

        return responseFashionPickupIncludeURI.build();
    }

    protected List<PostImageEntity> postImageDtoListToPostImageEntityList(List<PostImageDto> list) {
        if ( list == null ) {
            return null;
        }

        List<PostImageEntity> list1 = new ArrayList<PostImageEntity>( list.size() );
        for ( PostImageDto postImageDto : list ) {
            list1.add( postImageMapper.postImageDtoToPostImageEntity( postImageDto ) );
        }

        return list1;
    }

    protected List<PostImageDto> postImageEntityListToPostImageDtoList(List<PostImageEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PostImageDto> list1 = new ArrayList<PostImageDto>( list.size() );
        for ( PostImageEntity postImageEntity : list ) {
            list1.add( postImageMapper.postImageEntityToPostImageDto( postImageEntity ) );
        }

        return list1;
    }
}
