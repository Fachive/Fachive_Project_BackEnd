package com.facaieve.backend.dto.post;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.entity.crossReference.FashionPickupEntityToTagEntity;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.user.UserEntity;
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
    @Schema(description = "image file 을 포함하는 패션픽업 POST DTO")
    @Builder
    public static class PostDto {

        @Schema(description ="작성자 식별자")
        Long userId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description = "카테고리")
        String categoryName;

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> tagList = new ArrayList<>();

        @Schema(description="URI for send to front end")
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
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String changedTitle;

        @Schema(description ="패션픽업 본문")
        String changedBody;

        @Schema(description = "카테고리")
        String changedCategoryName;

        @Schema(description = "게시글 태그")
        List<TagDTO.PostTagDTO> changedTagList = new ArrayList<>();

        @Schema(description="URI for send to front end")
        List<MultipartFile> changedMultipartFileList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "기존 패션 픽업 게시물을 변경하기 위한 PATCH DTO")
    @Builder
    public static class PatchDto {


        @Schema(description ="패션픽업 제목")
        String changedTitle;

        @Schema(description ="패션픽업 본문")
        String changedBody;

        @Schema(description = "카테고리")
        CategoryEntity categoryEntity;

        @Schema(description = "패션픽업-태그 중간 엔티티")
        List<FashionPickupEntityToTagEntity> tagEntities;

        @Schema(description="URI for send to front end")
        List<S3ImageInfo> s3ImgInfo;
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
        List<S3ImageInfo> s3ImageInfoList = new ArrayList<>();
    }

    //dto for send to frontend so there is URI List
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "image file 을 포함하는 패션픽업 request DTO")
    @Builder
    public static class FashionPickupDtoForEntity {
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views = 0;

        @Schema(description ="추천수")
        Integer myPicks = 0;

        @Schema(description="태그")
        List<TagDTO.ResponseTagDTO> responseTagDTOList = new ArrayList<>();

        @Schema(description = "카테고리")
        CategoryDTO.ResponseCategoryDTO responseCategoryDTO;

        @Schema(description="URI for send to front end")
        List<S3ImageInfo> s3ImageInfoList = new ArrayList<>();


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
    @Builder
    public static class ResponseFashionPickupDtoForEntities {
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description ="추천수")
        Integer myPicks = 0;

        @Schema(description = "게시글 태그")
        List<TagDTO.ResponseTagDTO> tagList = new ArrayList<>();

        @Schema(description ="이미지 데이터 uri")
        String thumpNailImageUri;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseFashionPickupDtoForEntity {
        @Schema(description ="패션픽업 게시글 식별자")
        Long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String body;

        @Schema(description ="조회수")
        Integer views;

        @Schema(description ="추천수")
        Integer myPicks = 0;


        @Schema(description = "게시글 태그")
        List<TagDTO.ResponseTagDTO> tagList = new ArrayList<>();

        @Schema(description ="이미지 데이터")
        List<String> s3ImageUriList = new ArrayList<>();

        @Schema(description = "댓글 객체")
        List<TotalCommentDTO.ResponseCommentDTO> responseCommentDTOList = new ArrayList<>();

        @Schema(description = "작성자 정보")
        UserDto.ResponseUserDto2 userInfo;
    }






}
