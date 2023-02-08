package com.facaieve.backend.dto.post;


import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.crossReference.FundingEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FundingDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "image file 을 포함하는 포트폴리오 request DTO")
    public static class PostDto {

        @NotNull
        @Schema(description ="작성자 식별자")
        Long userId;

        @NotNull
        @Schema(description ="펀딩 제목")
        String title;

        @NotNull
        @Schema(description ="펀딩 본문")
        String body;

        @NotNull
        @Schema(description = "카테고리")
        String categoryName;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> tagList = new ArrayList<>();

        @Schema(description = "사진 URI")
        List<MultipartFile> multipartFileList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "기존 패션 픽업 게시물을 변경하기 위한 PATCH RequestDto")
    @Builder
    public static class PatchRequestDto {

        @Schema(description ="작성자 식별자")
        Long userId;

        @Schema(description ="수정할 게시물 식별자")
        Long fundingEntityId;

        @Schema(description ="패션픽업 제목")
        String changedTitle;

        @Schema(description ="패션픽업 본문")
        String changedBody;

        @Schema(description = "카테고리")
        String changedCategoryName;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> changedTagList = new ArrayList<>();

        @Schema(description="URI for send to front end")
        List<MultipartFile> changedMultipartFileList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "기존 패션 픽업 게시물을 변경하기 위한 PATCH RequestDto")
    @Builder
    public static class PatchDto {

        @Schema(description ="작성자 식별자")
        Long userId;

        @Schema(description ="수정할 게시물 식별자")
        Long fundingEntityId;

        @Schema(description ="패션픽업 제목")
        String changedTitle;

        @Schema(description ="패션픽업 본문")
        String changedBody;

        @Schema(description = "카테고리")
        CategoryEntity changedCategoryEntity;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

        @Schema(description = "게시글 태그")
        List<FundingEntityToTagEntity> changedTagList = new ArrayList<>();

        @Schema(description="URI for send to front end")
        List<S3ImageInfo> s3ImgInfo;
    }




















    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponsePortfolioDto{

        @Schema(description ="포트폴리오 식별자")
        Long portfolioEntityId;

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="포트폴리오 조회수")
        Integer views;
    }




    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostFundingDto{

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String Body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchFundingDto{

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String Body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFundingDto{

        @Schema(description ="펀딩 게시글 식별자")
        Long fundingEntityId;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteDto {

        @Schema(description ="펀딩 게시글 식별자")
        Long fundingEntityId;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFundingDto{  //더이상 사용 x ResponseFundingDtoForEntity로 대체

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String Body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

    }













    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseFundingDtoForEntity {
        @Schema(description ="패션픽업 게시글 식별자")
        Long fundingEntityId;

        @Schema(description ="패션픽업 작성자의 식별자")
        Long userEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description ="추천수")
        Integer myPicks = 0;

        @Column
        @Schema(description = "펀딩 마감일")
        LocalDateTime dueDate;

        @Column
        @Schema(description = "펀딩 목표액")
        Long targetPrice = 0L;//펀딩 목표금액

        @Column
        @Schema(description = "펀딩 모금액")
        Long fundedPrice = 0L;//펀딩된 현재 금액

        @Column
        @Schema(description = "펀딩 금액 퍼센트")
        Double percentage;

        @Schema(description = "게시글 태그")
        List<String> tagList = new ArrayList<>();

        @Schema(description ="이미지 데이터")
        List<String> s3ImageUriList = new ArrayList<>();

        @Schema(description ="댓글 데이터")
        List<FundingCommentEntity> commentEntities = new ArrayList<>();
    }
}
