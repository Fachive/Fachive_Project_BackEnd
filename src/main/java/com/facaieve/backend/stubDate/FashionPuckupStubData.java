package com.facaieve.backend.stubDate;

import com.facaieve.backend.entity.basetime.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FashionPuckupStubData extends BaseEntity {

    long fashionPickupEntityId = 100;

    String title = "패션 픽업 제목 테스트" ;

    String body = "패션 픽업 내용 테스트";

    int views = 100;
}
