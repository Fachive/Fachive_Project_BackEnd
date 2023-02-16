package com.facaieve.backend.service.email;

import com.facaieve.backend.entity.email.EmailTokenEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.EmailTokenRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailTokenService {
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    EmailTokenRepository emailTokenRepository;
    //todo value 값으로 설정해서 Localprofile 과  deploy 환경에 따라서 이메일을 보내는 주소를 다르게 만들것
    // 이메일 인증 토큰 생성

    public String createEmailToken(String userEmail) {

        Assert.notNull(userEmail, "식별자로 사용할 userEmail 을 설정해야합니다 ");
        // 이메일 토큰 저장
        EmailTokenEntity emailToken = EmailTokenEntity.createEmailToken(userEmail);
        emailTokenRepository.save(emailToken);

        // 이메일 전송
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("fachievhelp@gmail.com");
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("FACHIEV 회원가입 이메일 인증");
        mailMessage.setText("인증 문자열: "+emailToken.getToken());//사용자에게 이메일 인증 문자열을 전송함
        log.info("이메일을 전송합니다");
        emailSenderService.sendEmail(mailMessage);

        return emailToken.getToken();    // 인증메일 전송 시 토큰 반환

    }

    // 유효한 토큰 가져오기
    public EmailTokenEntity findByTokenAndExpiryDateAfterAndExpired(String token) throws BusinessLogicException {

        //expired 되기 전의 token 을 찾음
        log.info("이메일 토큰을 찾습니다");
        Optional<EmailTokenEntity> emailToken = emailTokenRepository
                .findByTokenAndExpiryDateAfterAndExpired(token, LocalDateTime.now(), false);

        // 토큰이 없다면 예외 발생
        return emailToken.orElseThrow(() -> new BusinessLogicException(ExceptionCode.EMAIL_TOKEN_NOT_FOUND));
    }

    public Optional<EmailTokenEntity> sendToFrontEndBeforeAuthentication(String userEmail){

        log.info("프론트 엔드에게 보낼 인증 코드를 가져옵니다.");
        Optional<EmailTokenEntity> emailToken = emailTokenRepository.findByUserEmailAndExpiryDateAfterAndExpired(
                userEmail,LocalDateTime.now(),false);

        return emailToken;
    }

}
