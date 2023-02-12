package com.facaieve.backend.dto.etc;

import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class CategoryDTO {


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseCategoryDTO{//응답
        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RequestCategoryDTO{//요청
        @Schema(description ="카테고리 식별자")
        Long categoryId;

        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostCategoryDto {

        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PatchCategoryDto {

        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetCategoryDto {
        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeleteCategoryDto {
        @Schema(description ="카테고리 이름, 카테고리(total, 상의, 아우터, 바지,원피스, 스커트, 액세서리, 정장, 드레스)")
        String categoryName;
    }
}
