package com.facaieve.backend.dto.etc;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


public class TagDTO {//수정
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseTagDTO {

        @Schema(description ="태그 이름")
        String tagName;

        @Schema(description ="태그 설명")
        String description;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostTagDTO {//생성
        @Schema(description ="태그 이름")
        String tagName;

        @Schema(description ="태그 설명")
        String description;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchTagDTO {//생성

        @Schema(description ="태그 이름")
        String tagName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    //patch 에서도 사용함.
    public static class GetTagDTO {//뭔가 필요할 거 같아서 만듬
        @Schema(description ="태그 식별자")
        Long tagId;

        @Schema(description ="태그 이름")
        String tagName;

        @Schema(description ="태그 설명")
        String description;

    }


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class deleteTagDTO{//삭제할때도 카테고리 이름으로 삭제함

        Long tagId;

        String tagName;

        String description;
    }



}
