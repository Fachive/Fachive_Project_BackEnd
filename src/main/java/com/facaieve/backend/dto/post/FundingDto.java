package com.facaieve.backend.dto.post;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
public class FundingDto {

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
