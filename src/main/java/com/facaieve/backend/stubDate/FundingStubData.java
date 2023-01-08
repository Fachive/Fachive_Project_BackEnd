package com.facaieve.backend.stubDate;

import com.facaieve.backend.entity.basetime.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundingStubData extends BaseEntity {

    Long fundingEntityId = 100L;
    String title = "펀딩 게시글 테스트 제목";
    String Body = "펀딩 게시글 테스트 내용";
    Date dueDate;
    Long targetPrice = 50000000L;//펀딩 목표금액
    Long fundedPrice = 30000000L;//펀딩된 현재 금액

}
