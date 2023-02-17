package com.facaieve.backend.dto.etc;
import com.facaieve.backend.Constant.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class MyPickDTO {
    //생성 삭제 이외에는 뭐가 더 필요할지...

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostMyPickDTO{//생성할땐 유저 아이디만 받음 다른 외래키 필요함.
        @Schema(description = "좋아요를 하는 사람")
        Long userId;
        @Schema(description = "좋아요의 대상이 되는 게시물, 댓글 엔티티 지정(PORTFOLIO,FASHIONPICKUP,FUNDING, PORTFOLIO COMMENT, FASHIONPICKUP COMMENT, FUNDING COMMENT)")
        PostType whatToPick;
        @Schema(description = "선택한 엔티티의 식별자")
        Long entityId;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class DeleteMyPickDTO{//삭제할때 유저 정보만을 받음 근데 이건 다른것도 필요할듯

        @Schema(description = "좋아요를 하는 사람")
        Long userId;
        @Schema(description = "좋아요의 대상이 되는 게시물, 댓글 엔티티 지정(PORTFOLIO,FASHIONPICKUP,FUNDING, PORTFOLIO COMMENT, FASHIONPICKUP COMMENT, FUNDING COMMENT)")
        PostType whatToPick;
        @Schema(description = "선택한 엔티티의 식별자")
        Long entityId;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ResponseMyPickDTO {//서버측에서 보내줄때

        Long myPickId;
        String Picked;
        Long entityId;
        Long userId;

    }

}
