package com.facaieve.backend.dto.post;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.entity.crossReference.FundingEntityToTagEntity;
import com.facaieve.backend.entity.crossReference.PortfolioEntityToTagEntity;
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

public class PortfolioDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "이미지 uri 를 포함하는 포트폴리오 response DTO")
    public static class ResponsePortfolioIncludeURI{

        @Schema(description ="포트폴리오 식별자")
        Long portfolioEntityId;

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="포트폴리오 조회수")
        Integer views;

        @Schema(description = "카테고리")
        CategoryDTO.ResponseCategoryDTO responseCategoryDTO;

        @Schema(description = "태그 리스트")
        List<TagDTO.ResponseTagDTO> responseTagDTOList = new ArrayList<>();

        @Schema(description = "포트폴리오에 들어갈 이미지 uri")
        List<S3ImageInfo> s3ImageInfoList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "MultipartFile 를 포함하는 포트폴리오 request DTO")
    public static class RequestPortfolioIncludeMultiPartFiles{

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="포트폴리오 조회수")
        Integer views;

        @Schema(description = "카테고리")
        CategoryDTO.PostCategoryDto postCategoryDto;

        @Schema
        List<TagDTO.PostTagDTO> postTagDTOList = new ArrayList<>();

        @Schema(description = "포트폴리오에 들어갈 이미지 uri")
        List<MultipartFile> multipartFileList = new ArrayList<>();
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "포트폴리오 response DTO")
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
    @Schema(description = "포트폴리오 등록 DTO")
    public static class PostDto{

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

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> tagList = new ArrayList<>();

        @Schema(description = "사진 URI")
        List<MultipartFile> multipartFileList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 수정 DTO")
    public static class PatchRequestDto {

        @NotNull
        @Schema(description ="작성자 식별자")
        Long userId;

        @Schema(description ="수정할 게시물 식별자")
        Long portfolioEntityId;

        @Schema(description ="패션픽업 제목")
        String changedTitle;

        @Schema(description ="패션픽업 본문")
        String changedBody;

        @Schema(description = "카테고리")
        String changedCategoryName;

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
    public static class PatchDto {

        @Schema(description ="작성자 식별자")
        Long userId;

        @Schema(description ="수정할 게시물 식별자")
        Long portfolioEntityId;

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
        List<PortfolioEntityToTagEntity> changedTagList = new ArrayList<>();

        @Schema(description="URI for send to front end")
        List<S3ImageInfo> s3ImgInfo;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 호출 DTO")
    public static class GetPortfolioDtoDto{

        @Schema(description ="포트폴리오 식별자")
        Long portfolioEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 삭제 DTO")
    public static class DeletePortfolioDtoDto{

        @Schema(description ="포트폴리오 식별자")
        Long portfolioEntityId;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponsePortfolioDtoForEntity {
        @Schema(description ="포트폴리오 게시글 식별자")
        Long portfolioEntityId;

        @Schema(description ="포트폴리오 작성자의 식별자")
        Long userEntityId;

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description ="추천수")
        Integer myPicks = 0;

        @Schema(description = "게시글 태그")
        List<String> tagList = new ArrayList<>();

        @Schema(description ="이미지 데이터")
        List<String> s3ImageUriList = new ArrayList<>();


    }



}
