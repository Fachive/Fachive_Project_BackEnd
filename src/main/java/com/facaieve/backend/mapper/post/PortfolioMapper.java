package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.stubDate.PortfolioStubData;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses ={
        PostImageMapper.class,
        CategoryMapper.class,
        TagMapper.class
})
public interface PortfolioMapper{
    // 포트폴리오 스텁데이터 -> 엔티티로 변환
    PortfolioEntity portfolioDtoToFashionPickupStubData(PortfolioStubData portfolioStubData);

    //patchDto -> Entity
    PortfolioEntity portfolioPatchDtoToPortfolioEntity(PortfolioDto.PatchRequestDto patchRequestDto);

    //getDto -> Entity
    PortfolioEntity portfolioGetDtoToPortfolioEntity(PortfolioDto.GetPortfolioDtoDto getPortfolioDtoDto);

    //deleteDto ->Entity
    PortfolioEntity portfolioDeleteDtoToPortfolioEntity(PortfolioDto.DeletePortfolioDtoDto deletePortfolioDtoDto);

    PortfolioDto.ResponsePortfolioDto portfolioEntityToResponsePortfolioEntity(PortfolioEntity portfolioEntity);

    default PortfolioDto.ResponsePortfolioDtoForEntity fundingEntityToResponseFundingDto(PortfolioEntity portfolioEntity){

        return PortfolioDto.ResponsePortfolioDtoForEntity
                .builder()
                .portfolioEntityId(portfolioEntity.getPortfolioEntityId())
                .title(portfolioEntity.getTitle())
                .body(portfolioEntity.getBody())
                .views(portfolioEntity.getViews())
                .myPicks(portfolioEntity.getMyPick().size())
                .tagList(portfolioEntity.getTagEntities().stream()
                        .map(tagEntity -> new TagDTO.ResponseTagDTO(tagEntity.getTagEntity().getTagName()))
                        .collect(Collectors.toList()))
                .s3ImageUriList(portfolioEntity.getS3ImgInfo().stream().map(S3ImageInfo::getFileURI).collect(Collectors.toList()))
                .build();
    }


}
