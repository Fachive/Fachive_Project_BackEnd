package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.stubDate.PortfolioStubData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring"
        ,uses ={ PostImageMapper.class})
public interface PortfolioMapper{
    // 포트폴리오 스텁데이터 -> 엔티티로 변환
    PortfolioEntity portfolioDtoToFashionPickupStubData(PortfolioStubData portfolioStubData);

    //postDto -> Entity
    PortfolioEntity portfolioPostDtoToPortfolioEntity(PortfolioDto.PostPortfolioDtoDto postPortfolioDtoDto);

    //patchDto -> Entity
    PortfolioEntity portfolioPatchDtoToPortfolioEntity(PortfolioDto.PatchPortfolioDtoDto patchPortfolioDtoDto);

    //getDto -> Entity
    PortfolioEntity portfolioGetDtoToPortfolioEntity(PortfolioDto.GetPortfolioDtoDto getPortfolioDtoDto);

    //deleteDto ->Entity
    PortfolioEntity portfolioDeleteDtoToPortfolioEntity(PortfolioDto.DeletePortfolioDtoDto deletePortfolioDtoDto);

    PortfolioDto.ResponsePortfolioDto portfolioEntityToResponsePortfolioEntity(PortfolioEntity portfolioEntity);

    @Mapping(source = "postImageDtoList", target = "postImageEntities")
    PortfolioEntity responsePortfolioIncludeURIToPortfolioEntity(
            PortfolioDto.ResponsePortfolioIncludeURI responsePortfolioIncludeURI);

    @Mapping(source = "postImageEntities", target = "postImageDtoList")
    @Mapping(source = "categoryEntity", target = "responseCategoryDTO")
    PortfolioDto.ResponsePortfolioIncludeURI portfolioEntityToResponsePortfolioIncludeURI(PortfolioEntity portfolio);




}
