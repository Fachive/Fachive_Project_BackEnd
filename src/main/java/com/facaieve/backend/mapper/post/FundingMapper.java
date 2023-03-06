package com.facaieve.backend.mapper.post;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.stubDate.FundingStubData;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses ={
        PostImageMapper.class,
        CategoryMapper.class,
        TagMapper.class
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
    FundingEntity fundingDeleteDtoToFundingEntity(FundingDto.DeleteDto deleteDto);

    FundingDto.ResponseFundingDto fundingEntityToResponseFundingEntity(FundingEntity fundingEntity);

    default TotalCommentDTO.ResponseCommentDTO fundingCommentEntityToResponseCommentDto(FundingCommentEntity fundingCommentEntity){
        return TotalCommentDTO.ResponseCommentDTO.builder()
                .commentId(fundingCommentEntity.getFundingCommentEntityId())
                .commentBody(fundingCommentEntity.getCommentBody())
                .postId(fundingCommentEntity.getPostId())
                .myPick(fundingCommentEntity.getMyPickEntity().size())
                .userId(fundingCommentEntity.getUserEntity().getUserEntityId())
                .commentProfileImageURI(fundingCommentEntity.getUserEntity().getProfileImg().getFileURI())
                .postType(fundingCommentEntity.getPostType())
                .build();
    }

    default FundingDto.ResponseFundingDtoForEntity fundingEntityToResponseFundingDto(FundingEntity fundingEntity){

        return FundingDto.ResponseFundingDtoForEntity
                .builder()
                .fundingEntityId(fundingEntity.getFundingEntityId())
                .title(fundingEntity.getTitle())
                .body(fundingEntity.getBody())
                .dueDate(fundingEntity.getDueDate())
                .fundedPrice(fundingEntity.getFundedPrice())
                .targetPrice(fundingEntity.getTargetPrice())
                .views(fundingEntity.getViews())
                .myPicks(fundingEntity.getMyPick().size())
                .tagList(fundingEntity.getTagEntities()
                        .stream().map(tagEntity -> new TagDTO.ResponseTagDTO(tagEntity.getTagEntity().getTagName())).collect(Collectors.toList()))
                .s3ImageUriList(fundingEntity.getS3ImgInfo()
                        .stream().map(S3ImageInfo::getFileURI).collect(Collectors.toList()))
                .responseCommentDTOList(fundingEntity.getCommentList().
                        stream().map(this::fundingCommentEntityToResponseCommentDto).collect(Collectors.toList()))
                .userInfo(UserDto.ResponseUserDto2.of(fundingEntity.getUserEntity(), fundingEntity.getUserEntity().getProfileImg().getFileURI()))
                .build();
    }



}
