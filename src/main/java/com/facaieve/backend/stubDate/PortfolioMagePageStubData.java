package com.facaieve.backend.stubDate;

import com.facaieve.backend.dto.image.PostImageDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PortfolioMagePageStubData {

    String displayName = "testName";
    int pickup = 999;
    int views = 9999;

    PostImageDto postImageDto = PostImageDto.builder()
            .fileName("fileName")
            .fileURI("https://facaiev-img.s3.ap-northeast-2.amazonaws.com/2023-01-23T18%3A14%3A09.499932_%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202023-01-23%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%206.13.34.png")
            .build();
}
