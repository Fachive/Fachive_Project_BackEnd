package com.facaieve.backend.dto.post;

import javax.persistence.*;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.image.PostImageDto;
import com.facaieve.backend.entity.etc.TagEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FashionPickupDto {
    //이미지, 댓글 , 태그 , 제목 , 추천수, 조회수,

    //post api 에 사용할 예정 todo controller 에 실제로 적용할것
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "image file 을 포함하는 패션픽업 response DTO")
    @Builder
    public static class RequestFashionPickupIncludeMultiPartFileDto{
//
//        @Schema(description ="패션픽업 게시글 식별자")
//        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description = "카테고리")
        CategoryDTO.PostCategoryDto postCategoryDto;

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> postTagDTOList = new ArrayList<>();

        @Schema(description="URI for send to front end")
        List<MultipartFile>  multipartFileList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "image file 을 포함하는 패션픽업 request DTO")
    @Builder
    public static class BuffFashionPickupIncludeURI{
        @Schema(description ="패션픽업 게시글 식별자")
        Long  fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description="URI for send to front end")
        List<PostImageDto>  postImageDtoList = new ArrayList<>();
    }

    //dto for send to frontend so there is URI List
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "image file 을 포함하는 패션픽업 request DTO")
    @Builder
    public static class ResponseFashionPickupIncludeURI{
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description ="추천수")
        Integer myPicks;

        @Schema(description="태그")
        List<TagDTO.ResponseTagDTO> responseTagDTOList = new ArrayList<>();

        @Schema(description = "카테고리")
        CategoryDTO.ResponseCategoryDTO responseCategoryDTO;

        @Schema(description="URI for send to front end")
        List<PostImageDto>  postImageDtoList = new ArrayList<>();
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostFashionPickupDto{

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;
    }





}
