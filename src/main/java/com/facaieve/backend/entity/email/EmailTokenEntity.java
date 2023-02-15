package com.facaieve.backend.entity.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailTokenEntity {//email token 을 저장하기 위한 table

    private static final long EMAIL_TOKEN_EXPIRATION_TIME_VALUE = 5L;    // 이메일 토큰 만료 시간

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(nullable = false)
    private String userEmail;// 회원가입 시도 하면 받을 거 todo Long type 으로 구현해야할 가능성 있음

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean expired;

    // 이메일 인증 토큰 생성
    public static EmailTokenEntity createEmailToken(String userEmail) {
        EmailTokenEntity emailTokenEntity = new EmailTokenEntity();
        emailTokenEntity.expiryDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME_VALUE); // 5분 후 만료
        emailTokenEntity.expired = false;
        emailTokenEntity.userEmail = userEmail;
        emailTokenEntity.token = UUID.randomUUID().toString();//렌덤한 문자열을 사용해서 새로운 token 으로 사용함.

        return emailTokenEntity;
    }

    // 토큰 만료
    public void setTokenToUsed() {
        this.expired = true;
    }
}

