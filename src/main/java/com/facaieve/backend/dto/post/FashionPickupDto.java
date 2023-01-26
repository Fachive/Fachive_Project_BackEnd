package com.facaieve.backend.dto.post;

import javax.persistence.*;

import com.facaieve.backend.dto.image.PostImageDto;
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

        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;

        @Schema(description ="조회수")
        int views;

        @Schema(description="URI for send to front end")
        List<MultipartFile>  multiPartFileList = new ArrayList<>();
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "image file 을 포함하는 패션픽업 request DTO")
    @Builder
    public static class BuffFashionPickupIncludeURI{
        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;

        @Schema(description ="조회수")
        int views;

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
        long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;

        @Schema(description ="조회수")
        int views;

        @Schema(description ="추천수")
        int myPicks;

        @Schema(description="URI for send to front end")
        List<PostImageDto>  multiPartFileList = new ArrayList<>();
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostFashionPickupDto{

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatchFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFashionPickupDto{
        @Schema(description ="패션픽업 게시글 식별자")
        long fashionPickupEntityId;

        @Schema(description ="패션픽업 제목")
        String title;

        @Schema(description ="패션픽업 본문")
        String Body;

        @Schema(description ="조회수")
        int views;
    }





}
