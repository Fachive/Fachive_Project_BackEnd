package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.stubDate.FundingStubData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses ={
        PostImageMapper.class
})
public interface FundingMapper extends PostMapper{
    // 포트폴리오 스텁데이터 -> 엔티티로 변환
    FundingEntity fundingDtoToFundingEntityStubData(FundingStubData fundingStubData);

    //postDto -> Entity
    FundingEntity fundingPostDtoToFundingEntity(FundingDto.PostFundingDto postFundingDto);

    //patchDto -> Entity
    FundingEntity fundingPatchDtoToFundingEntity(FundingDto.PatchFundingDto patchFundingDto);

    //getDto -> Entity
    FundingEntity fundingGetDtoToFundingEntity(FundingDto.GetFundingDto getFundingDto);

    //deleteDto ->Entity
    FundingEntity fundingDeleteDtoToFundingEntity(FundingDto.DeleteFundingDto deleteFundingDto);

    FundingDto.ResponseFundingDto fundingEntityToResponseFundingEntity(FundingEntity fundingEntity);

    @Mapping(source = "multiPartFileList", target = "postImageEntities")
    FundingEntity ResponseFundingIncludeURIToFundingEntity(FundingDto.ResponseFundingIncludeURI responseFundingIncludeURI);

    @Mapping(source = "postImageEntities", target = "multiPartFileList")
    FundingDto.ResponseFundingIncludeURI FundingEntityToResponseFundingIncludeURI(FundingEntity fundingEntity);




}
