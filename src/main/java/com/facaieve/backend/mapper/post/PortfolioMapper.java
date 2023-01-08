package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.stubDate.PortfolioStubData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
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




}
