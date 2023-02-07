package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.stubDate.FundingStubData;
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
public class FundingMapperImpl implements FundingMapper {

    @Autowired
    private PostImageMapper postImageMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Object entityDtoToEntityStubData(Object entityStubData) {
        if ( entityStubData == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object postDtoToEntity(Object postDto) {
        if ( postDto == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object patchDtoToEntity(Object patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object getDtoToEntity(Object getDto) {
        if ( getDto == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object deleteDtoToEntity(Object deleteDto) {
        if ( deleteDto == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object entityToResponseDto(Object entity) {
        if ( entity == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object entityIncludeURIToEntity(Object dtoWithUri) {
        if ( dtoWithUri == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public Object entityToResponseEntityIncludeURI(Object dtoWithUri) {
        if ( dtoWithUri == null ) {
            return null;
        }

        Object object = new Object();

        return object;
    }

    @Override
    public FundingEntity fundingDtoToFundingEntityStubData(FundingStubData fundingStubData) {
        if ( fundingStubData == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setRegTime( fundingStubData.getRegTime() );
        fundingEntity.setUpdateTime( fundingStubData.getUpdateTime() );
        fundingEntity.setFundingEntityId( fundingStubData.getFundingEntityId() );
        fundingEntity.setTitle( fundingStubData.getTitle() );
        fundingEntity.setBody( fundingStubData.getBody() );
        fundingEntity.setDueDate( fundingStubData.getDueDate() );
        fundingEntity.setTargetPrice( fundingStubData.getTargetPrice() );
        fundingEntity.setFundedPrice( fundingStubData.getFundedPrice() );

        return fundingEntity;
    }

    @Override
    public FundingEntity fundingPostDtoToFundingEntity(FundingDto.PostFundingDto postFundingDto) {
        if ( postFundingDto == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setTitle( postFundingDto.getTitle() );
        fundingEntity.setBody( postFundingDto.getBody() );
        fundingEntity.setTargetPrice( postFundingDto.getTargetPrice() );
        fundingEntity.setFundedPrice( postFundingDto.getFundedPrice() );

        return fundingEntity;
    }

    @Override
    public FundingEntity fundingPatchDtoToFundingEntity(FundingDto.PatchFundingDto patchFundingDto) {
        if ( patchFundingDto == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setTitle( patchFundingDto.getTitle() );
        fundingEntity.setBody( patchFundingDto.getBody() );
        fundingEntity.setTargetPrice( patchFundingDto.getTargetPrice() );
        fundingEntity.setFundedPrice( patchFundingDto.getFundedPrice() );

        return fundingEntity;
    }

    @Override
    public FundingEntity fundingGetDtoToFundingEntity(FundingDto.GetFundingDto getFundingDto) {
        if ( getFundingDto == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setFundingEntityId( getFundingDto.getFundingEntityId() );

        return fundingEntity;
    }

    @Override
    public FundingEntity fundingDeleteDtoToFundingEntity(FundingDto.DeleteFundingDto deleteFundingDto) {
        if ( deleteFundingDto == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setFundingEntityId( deleteFundingDto.getFundingEntityId() );

        return fundingEntity;
    }

    @Override
    public FundingDto.ResponseFundingDto fundingEntityToResponseFundingEntity(FundingEntity fundingEntity) {
        if ( fundingEntity == null ) {
            return null;
        }

        FundingDto.ResponseFundingDto responseFundingDto = new FundingDto.ResponseFundingDto();

        responseFundingDto.setTitle( fundingEntity.getTitle() );
        responseFundingDto.setBody( fundingEntity.getBody() );
        responseFundingDto.setTargetPrice( fundingEntity.getTargetPrice() );
        responseFundingDto.setFundedPrice( fundingEntity.getFundedPrice() );

        return responseFundingDto;
    }

    @Override
    public FundingEntity ResponseFundingIncludeURIToFundingEntity(FundingDto.ResponseFundingIncludeURI responseFundingIncludeURI) {
        if ( responseFundingIncludeURI == null ) {
            return null;
        }

        FundingEntity fundingEntity = new FundingEntity();

        fundingEntity.setPostImageEntities( postImageDtoListToPostImageEntityList( responseFundingIncludeURI.getPostImageDtoList() ) );
        fundingEntity.setTitle( responseFundingIncludeURI.getTitle() );
        fundingEntity.setBody( responseFundingIncludeURI.getBody() );
        fundingEntity.setTargetPrice( responseFundingIncludeURI.getTargetPrice() );
        fundingEntity.setFundedPrice( responseFundingIncludeURI.getFundedPrice() );
        fundingEntity.setViews( responseFundingIncludeURI.getViews() );
        fundingEntity.setMyPicks( responseFundingIncludeURI.getMyPicks() );

        return fundingEntity;
    }

    @Override
    public FundingDto.ResponseFundingIncludeURI FundingEntityToResponseFundingIncludeURI(FundingEntity fundingEntity) {
        if ( fundingEntity == null ) {
            return null;
        }

        FundingDto.ResponseFundingIncludeURI.ResponseFundingIncludeURIBuilder responseFundingIncludeURI = FundingDto.ResponseFundingIncludeURI.builder();

        responseFundingIncludeURI.postImageDtoList( postImageEntityListToPostImageDtoList( fundingEntity.getPostImageEntities() ) );
        responseFundingIncludeURI.responseCategoryDTO( categoryMapper.categoryEntityToResponseCategoryDto( fundingEntity.getCategoryEntity() ) );
        responseFundingIncludeURI.title( fundingEntity.getTitle() );
        responseFundingIncludeURI.body( fundingEntity.getBody() );
        responseFundingIncludeURI.views( fundingEntity.getViews() );
        responseFundingIncludeURI.myPicks( fundingEntity.getMyPicks() );
        responseFundingIncludeURI.targetPrice( fundingEntity.getTargetPrice() );
        responseFundingIncludeURI.fundedPrice( fundingEntity.getFundedPrice() );

        return responseFundingIncludeURI.build();
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
