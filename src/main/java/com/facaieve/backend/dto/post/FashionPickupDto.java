package com.facaieve.backend.dto.post;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class FashionPickupDto {

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
