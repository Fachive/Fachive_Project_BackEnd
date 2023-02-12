package com.facaieve.backend.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MainPostDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseMainDtoForEntity {

        @Schema(description ="포트폴리오 게시글 식별자")
        List<FashionPickupDto.ResponseFashionPickupDtoForEntity> fashionpickList = new ArrayList<>();

        @Schema(description ="포트폴리오 작성자의 식별자")
        List<FundingDto.ResponseFundingDtoForEntity> fundingList= new ArrayList<>();



    }
}
