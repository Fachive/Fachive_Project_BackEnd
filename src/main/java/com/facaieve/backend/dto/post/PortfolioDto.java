package com.facaieve.backend.dto.post;

import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.image.PostImageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

        @Schema(description = "포트폴리오에 들어갈 이미지 uri")
        List<PostImageDto> postImageDtoList = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "MultipartFile 를 포함하는 포트폴리오 request DTO")
    public static class RequestPortfolioIncludeMultiPartFiles{

        @Schema(description ="포트폴리오 식별자")
        Long portfolioEntityId;

        @Schema(description ="포트폴리오 제목")
        String title;

        @Schema(description ="포트폴리오 본문")
        String body;

        @Schema(description ="포트폴리오 조회수")
        Integer views;

        @Schema(description = "카테고리")
        CategoryDTO.PostCategoryDto postCategoryDto;

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
    public static class PostPortfolioDtoDto{

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
    @Schema(description = "포트폴리오 수정 DTO")
    public static class PatchPortfolioDtoDto{

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


}
