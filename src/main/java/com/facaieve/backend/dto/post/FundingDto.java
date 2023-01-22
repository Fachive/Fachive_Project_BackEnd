package com.facaieve.backend.dto.post;


import com.facaieve.backend.dto.image.PostImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
public class FundingDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "image file 을 포함하는 포트폴리오 response DTO")
    public static class ResponseFundingIncludeURI{

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

        @Schema(description="URI for send to front end")
        List<PostImageDto>  multiPartFileList = new ArrayList<>();
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "image file 을 포함하는 포트폴리오 request DTO")
    public static class RequestFundingIncludeMultiPartFileDto{

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

        @Schema(description = "사진 URI")
        List<MultipartFile> multiPartFiles = new ArrayList<>();
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponsePortfolioDto{

        @Schema(description ="포트폴리오 식별자")
        long portfolioEntityId;

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="포트폴리오 조회수")
        int views;
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
        long fundingEntityId;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteFundingDto{

        @Schema(description ="펀딩 게시글 식별자")
        long fundingEntityId;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseFundingDto{

        @Schema(description ="펀딩 제목")
        String title;

        @Schema(description ="펀딩 본문")
        String Body;

        @Schema(description ="펀딩 목표액")
        Long targetPrice;//펀딩 목표금액

        @Schema(description ="펀딩 모금액")
        Long fundedPrice;//펀딩된 현재 금액

    }

}
