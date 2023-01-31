package com.facaieve.backend.stubDate;

import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.image.PostImageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingMainPageStubData {

    Long fundingEntityId = 100L;
    String title = "펀딩 게시글 테스트 제목";
    String Body = "펀딩 게시글 테스트 내용";

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy hh:mm:ss a", timezone = "Asia/Seoul")
    Timestamp dueDate = Timestamp
            .valueOf(LocalDateTime.of(2023,12,31,12,12)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    List<TagDTO.GetTagDTO> tags;
    Long targetPrice = 50000000L;//펀딩 목표금액
    Long fundedPrice = 30000000L;//펀딩된 현재 금액
    Integer percentage = (int) (targetPrice/fundedPrice)*100;

    PostImageDto postImageDto = PostImageDto.builder()
            .fileName("fileName")
            .fileURI("https://facaiev-img.s3.ap-northeast-2.amazonaws.com/2023-01-23T18%3A14%3A09.499932_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202023-01-23%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%206.13.34.png")
            .build();

}
