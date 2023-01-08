package com.facaieve.backend.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PortfolioDto {



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 등록 DTO")
    public static class PostPortfolioDtoDto{

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
    @Schema(description = "포트폴리오 수정 DTO")
    public static class PatchPortfolioDtoDto{

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
    @Schema(description = "포트폴리오 호출 DTO")
    public static class GetPortfolioDtoDto{

        @Schema(description ="포트폴리오 식별자")
        long portfolioEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 삭제 DTO")
    public static class DeletePortfolioDtoDto{

        @Schema(description ="포트폴리오 식별자")
        long portfolioEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "포트폴리오 response DTO")
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

}
