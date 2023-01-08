package com.facaieve.backend.stubDate;

import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PortfolioStubData {

    Long portfolioEntityId = 1L;

    String title = "포트폴리오 게시글 제목";

    String body = "포트폴리오 게시글 내용";

    int views = 100;

}
