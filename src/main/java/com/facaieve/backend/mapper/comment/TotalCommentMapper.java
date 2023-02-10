package com.facaieve.backend.mapper.comment;

import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.mapper.etc.CategoryMapper;
import com.facaieve.backend.mapper.etc.TagMapper;
import com.facaieve.backend.mapper.post.PostImageMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;

@Mapper(componentModel = "spring", uses ={
        PostImageMapper.class,
        CategoryMapper.class,
        TagMapper.class
})
public interface TotalCommentMapper {

    default FashionPickUpCommentEntity totalPostCommentDtoToFashionPickupCommentEntity(TotalCommentDTO.PostCommentDTO postCommentDTO){
        //userEntity, fashionPickupEntity 는 서비스 레이어 에서 처리함
        return FashionPickUpCommentEntity.builder()
                .postId(postCommentDTO.getPostId())
                .postType(postCommentDTO.getPostType())
                .commentBody(postCommentDTO.getCommentBody())
                .build();
    }

    default TotalCommentDTO.ResponseCommentDTO fashionCommentEntityToResponseCommentDto(FashionPickUpCommentEntity fashionPickUpCommentEntity){

        return TotalCommentDTO.ResponseCommentDTO.builder()
                .commentId(fashionPickUpCommentEntity.getFashionPickupCommentEntityId())
                .myPick(fashionPickUpCommentEntity.getMyPickEntity().size())
                .postId(fashionPickUpCommentEntity.getPostId())
                .commentBody(fashionPickUpCommentEntity.getCommentBody())
                .userId(fashionPickUpCommentEntity.getUserEntity().getUserEntityId())
                .commentProfileImageURI(fashionPickUpCommentEntity.getUserEntity().getProfileImg().getFileURI())
                .postType(fashionPickUpCommentEntity.getPostType())
                .build();
    }

    default FundingCommentEntity totalPostCommentDtoToFundingCommentEntity(TotalCommentDTO.PostCommentDTO postCommentDTO){
        return FundingCommentEntity.builder()
                .postId(postCommentDTO.getPostId())
                .postType(postCommentDTO.getPostType())
                .commentBody(postCommentDTO.getCommentBody())
                .build();
    }

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

    default PortfolioCommentEntity totalPostCommentDtoToPortfolioCommentEntity(TotalCommentDTO.PostCommentDTO postCommentDTO){
        return PortfolioCommentEntity.builder()
                .commentBody(postCommentDTO.getCommentBody())
                .postId(postCommentDTO.getPostId())
                .userId(postCommentDTO.getUserId())
                .postType(postCommentDTO.getPostType()).build();
    }

    default TotalCommentDTO.ResponseCommentDTO portfolioCommentEntityToResponseCommentDto(PortfolioCommentEntity portfolioCommentEntity){
        return TotalCommentDTO.ResponseCommentDTO.builder()
                .commentId(portfolioCommentEntity.getPortfolioCommentEntityId())
                .postId(portfolioCommentEntity.getPostId())
                .postType(portfolioCommentEntity.getPostType())
                .userId(portfolioCommentEntity.getUserId())
                .commentProfileImageURI(portfolioCommentEntity.getUserEntity().getProfileImg().getFileURI())
                .myPick(portfolioCommentEntity.getMyPickEntity().size())
                .commentBody(portfolioCommentEntity.getCommentBody()).build();
    }
}
