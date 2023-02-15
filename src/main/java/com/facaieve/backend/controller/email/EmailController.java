package com.facaieve.backend.controller.email;

import com.facaieve.backend.dto.email.EmailSuccessDTO;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.service.email.EmailService;
import com.facaieve.backend.service.email.EmailTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated // email token entity 에서
@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/confirm-email")
    public ResponseEntity<?> viewConfirmEmail(@Valid @RequestParam String token) { // token 이 Null 값이라면 오를 발생시킨다.
        try {
            EmailSuccessDTO.Answer result = emailService.verifyEmail(token);//토큰을 확인하는 서비스
            return ResponseEntity.ok().body(result);
        } catch (BusinessLogicException exception) {
            throw new BusinessLogicException(ExceptionCode.MISS_TOKEN);
        }
    }

}
