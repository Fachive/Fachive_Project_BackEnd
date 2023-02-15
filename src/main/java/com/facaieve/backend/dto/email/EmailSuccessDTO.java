package com.facaieve.backend.dto.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmailSuccessDTO {

    private static final String SUCCESSMSG = "이메일 인증에 성공하셨습니다";
    private static final String FAILMSG = "이메일 인증에 실패하셨습니다";

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Answer {
        String userEmail;
        String msg;

        public Answer(String userEmail, boolean verification) {
            this.userEmail = userEmail;
            if (verification) {
                this.msg = "userId: " + userEmail + " " + SUCCESSMSG;
            }
        }

        public Answer(boolean verification) {
            this.msg = FAILMSG;
        }

    }
}
