package com.facaieve.backend.dto.etc;
import lombok.*;

public class MyPickDTO {
    //생성 삭제 이외에는 뭐가 더 필요할지...

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostMyPickDTO{//생성할땐 유저 아이디만 받음 다른 외래키 필요함.
        Long userId;
        Enum whatToPick;
        Long entityId;

        public enum PickableEntity{
            Portfolio("포트폴리오"),
            FashionPickUp("패션픽업"),
            Funding("펀딩"),
            PortfolioComment("포트폴리오 댓글"),
            FashionPickUpComment("패션픽업 댓글"),
            FundingComment("펀딩 팻들");

            @Getter
            private final String entityType;

            PickableEntity(String entityType) {
                this.entityType =entityType;
            }
        }

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class deleteMyPickDTO{//삭제할때 유저 정보만을 받음 근데 이건 다른것도 필요할듯

        Long userId;
        String whatToUnPick;
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
