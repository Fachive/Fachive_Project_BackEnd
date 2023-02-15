package com.facaieve.backend.service.email;

import com.facaieve.backend.dto.email.EmailSuccessDTO;
import com.facaieve.backend.entity.email.EmailTokenEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
     EmailTokenService emailTokenService;
    @Autowired
     UserService userService;

    @Transactional
    public EmailSuccessDTO.Answer verifyEmail(String token) throws BusinessLogicException {
        // 이메일 토큰을 찾아옴
        EmailTokenEntity findEmailToken = emailTokenService.findByTokenAndExpiryDateAfterAndExpired(token);
        // TODO: 여기서부터는 이메일 성공 인증 로직을 구현합니다.
        // 토큰의 유저 ID를 이용하여 유저 인증 정보를 가져온다.
        Optional<UserEntity> findUser = userService.findByEmail(findEmailToken.getUserEmail());
        findEmailToken.setTokenToUsed();    // 사용 완료

        if (findUser.isPresent()) {
            UserEntity user = findUser.get();
                    user.emailVerifiedSuccess();//멤버의 인증 변수를 ture 로 전환함
            EmailSuccessDTO.Answer success = new EmailSuccessDTO.Answer(user.getEmail(), true);
                    return success;
        } else {
            EmailSuccessDTO.Answer success = new EmailSuccessDTO.Answer(false);
            return success;
        }
    }
}
