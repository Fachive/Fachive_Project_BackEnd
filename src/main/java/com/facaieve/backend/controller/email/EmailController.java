package com.facaieve.backend.controller.email;

import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.dto.email.EmailSuccessDTO;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.service.email.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "회원가입 후 이메일 검증을 위한 토큰값을 입력하는 api",
            description = "사용자는 회원가입 중 메일로 받은 인증 토큰을 확인하고 이를 이 api를 통해 입력해야 한다. " +
                    "을 이용해서 댓들을 달 수 있음")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({@ApiResponse(responseCode = "200", description = "이메일이 인증이 정상등록되었습니다. ", content = @Content(schema = @Schema(implementation = TotalCommentDTO.ResponseCommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("/confirm-email") // test pass
    public ResponseEntity<?> viewConfirmEmail(@Valid @RequestParam String token) { // token 이 Null 값이라면 오를 발생시킨다.
        try {
            EmailSuccessDTO.Answer result = emailService.verifyEmail(token);//토큰을 확인하는 서비스
            return ResponseEntity.ok().body(result);
        } catch (BusinessLogicException exception) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_AUTHENTICATION_NEED);
        }
    }

}
