package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.image.PostImageEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.stubDate.PortfolioStubData;
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
public class PortfolioMapperImpl implements PortfolioMapper {

    @Autowired
    private PostImageMapper postImageMapper;

    @Override
    public PortfolioEntity portfolioDtoToFashionPickupStubData(PortfolioStubData portfolioStubData) {
        if ( portfolioStubData == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setPortfolioEntityId( portfolioStubData.getPortfolioEntityId() );
        portfolioEntity.setTitle( portfolioStubData.getTitle() );
        portfolioEntity.setBody( portfolioStubData.getBody() );
        portfolioEntity.setViews( portfolioStubData.getViews() );

        return portfolioEntity;
    }

    @Override
    public PortfolioEntity portfolioPostDtoToPortfolioEntity(PortfolioDto.PostPortfolioDtoDto postPortfolioDtoDto) {
        if ( postPortfolioDtoDto == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setTitle( postPortfolioDtoDto.getTitle() );
        portfolioEntity.setBody( postPortfolioDtoDto.getBody() );
        portfolioEntity.setViews( postPortfolioDtoDto.getViews() );

        return portfolioEntity;
    }

    @Override
    public PortfolioEntity portfolioPatchDtoToPortfolioEntity(PortfolioDto.PatchPortfolioDtoDto patchPortfolioDtoDto) {
        if ( patchPortfolioDtoDto == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setPortfolioEntityId( patchPortfolioDtoDto.getPortfolioEntityId() );
        portfolioEntity.setTitle( patchPortfolioDtoDto.getTitle() );
        portfolioEntity.setBody( patchPortfolioDtoDto.getBody() );
        portfolioEntity.setViews( patchPortfolioDtoDto.getViews() );

        return portfolioEntity;
    }

    @Override
    public PortfolioEntity portfolioGetDtoToPortfolioEntity(PortfolioDto.GetPortfolioDtoDto getPortfolioDtoDto) {
        if ( getPortfolioDtoDto == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setPortfolioEntityId( getPortfolioDtoDto.getPortfolioEntityId() );

        return portfolioEntity;
    }

    @Override
    public PortfolioEntity portfolioDeleteDtoToPortfolioEntity(PortfolioDto.DeletePortfolioDtoDto deletePortfolioDtoDto) {
        if ( deletePortfolioDtoDto == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setPortfolioEntityId( deletePortfolioDtoDto.getPortfolioEntityId() );

        return portfolioEntity;
    }

    @Override
    public PortfolioDto.ResponsePortfolioDto portfolioEntityToResponsePortfolioEntity(PortfolioEntity portfolioEntity) {
        if ( portfolioEntity == null ) {
            return null;
        }

        PortfolioDto.ResponsePortfolioDto.ResponsePortfolioDtoBuilder responsePortfolioDto = PortfolioDto.ResponsePortfolioDto.builder();

        responsePortfolioDto.portfolioEntityId( portfolioEntity.getPortfolioEntityId() );
        responsePortfolioDto.title( portfolioEntity.getTitle() );
        responsePortfolioDto.body( portfolioEntity.getBody() );
        responsePortfolioDto.views( portfolioEntity.getViews() );

        return responsePortfolioDto.build();
    }

    @Override
    public PortfolioEntity responsePortfolioIncludeURIToPortfolioEntity(PortfolioDto.ResponsePortfolioIncludeURI responsePortfolioIncludeURI) {
        if ( responsePortfolioIncludeURI == null ) {
            return null;
        }

        PortfolioEntity portfolioEntity = new PortfolioEntity();

        portfolioEntity.setPostImageEntities( postImageDtoListToPostImageEntityList( responsePortfolioIncludeURI.getPostImageDtoList() ) );
        portfolioEntity.setPortfolioEntityId( responsePortfolioIncludeURI.getPortfolioEntityId() );
        portfolioEntity.setTitle( responsePortfolioIncludeURI.getTitle() );
        portfolioEntity.setBody( responsePortfolioIncludeURI.getBody() );
        portfolioEntity.setViews( responsePortfolioIncludeURI.getViews() );

        return portfolioEntity;
    }

    @Override
    public PortfolioDto.ResponsePortfolioIncludeURI portfolioEntityToResponsePortfolioIncludeURI(PortfolioEntity portfolio) {
        if ( portfolio == null ) {
            return null;
        }

        PortfolioDto.ResponsePortfolioIncludeURI.ResponsePortfolioIncludeURIBuilder responsePortfolioIncludeURI = PortfolioDto.ResponsePortfolioIncludeURI.builder();

        responsePortfolioIncludeURI.postImageDtoList( postImageEntityListToPostImageDtoList( portfolio.getPostImageEntities() ) );
        responsePortfolioIncludeURI.responseCategoryDTO( categoryEntityToResponseCategoryDTO( portfolio.getCategoryEntity() ) );
        responsePortfolioIncludeURI.portfolioEntityId( portfolio.getPortfolioEntityId() );
        responsePortfolioIncludeURI.title( portfolio.getTitle() );
        responsePortfolioIncludeURI.body( portfolio.getBody() );
        responsePortfolioIncludeURI.views( portfolio.getViews() );

        return responsePortfolioIncludeURI.build();
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

    protected CategoryDTO.ResponseCategoryDTO categoryEntityToResponseCategoryDTO(CategoryEntity categoryEntity) {
        if ( categoryEntity == null ) {
            return null;
        }

        CategoryDTO.ResponseCategoryDTO.ResponseCategoryDTOBuilder responseCategoryDTO = CategoryDTO.ResponseCategoryDTO.builder();

        responseCategoryDTO.categoryName( categoryEntity.getCategoryName() );

        return responseCategoryDTO.build();
    }
}
