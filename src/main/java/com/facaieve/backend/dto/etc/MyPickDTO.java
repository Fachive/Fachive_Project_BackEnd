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
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class deleteMyPickDTO{//삭제할때 유저 정보만을 받음 근데 이건 다른것도 필요할듯

      Long userId;

    }

//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Getter
//    @Setter
//    public static class getMyPickDTO{
//        Long userId;
//        Long myPickId;
//    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ResponseMyPickDTO {//서버측에서 보내줄때

        Long myPickId;

        Long userId;

    }

}
