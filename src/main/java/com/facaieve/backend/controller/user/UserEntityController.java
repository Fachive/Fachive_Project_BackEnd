package com.facaieve.backend.controller.user;


import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.UserDto.PostUserDto;
import com.facaieve.backend.dto.multi.Multi_ResponseDTO;
import com.facaieve.backend.entity.email.EmailTokenEntity;
import com.facaieve.backend.entity.image.ImageEntityProfile;
import com.facaieve.backend.entity.image.S3ImageInfo;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.mapper.user.UserMapper;
import com.facaieve.backend.security.TokenProvider;
import com.facaieve.backend.service.aswS3.S3FileService;
import com.facaieve.backend.service.email.EmailTokenService;
import com.facaieve.backend.service.image.ImageService;
import com.facaieve.backend.service.user.UserService;
import com.nimbusds.oauth2.sdk.AuthorizationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.apache.catalina.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import javax.transaction.Transactional;

import java.io.*;

import java.util.List;



@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@RequiredArgsConstructor
//@Tag(name = "UserEntity", description = "사용자와 관련된 api")
public class UserEntityController {
    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    S3FileService s3FileService;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailTokenService emailTokenService;//이메일 토큰을 만들어서 인증하는 서비스 클래스

    @Autowired
    AuthenticationManager authenticationManager;

    //todo 로그인 할 때 이메일 인증 여부 검증하는 메소드 작성할것
    //todo 이메일을 전송하는 주소 달리 할것
//
//    @GetMapping
//    public ResponseEntity<?> getTest(@AuthenticationPrincipal String userEmail){
//        return ResponseEntity.ok().body(userEmail);
//    }
//    @GetMapping("/auth/geteamilauthentication") // todo 프론트 엔드와 협의 후 삭제요망
//    public ResponseEntity<?> getUserEmailAuthenticationToken(@RequestParam String userEmail){
//        Optional<EmailTokenEntity> emailToken = emailTokenService.sendToFrontEndBeforeAuthentication(userEmail);
//        if(emailToken.isPresent()){
//            return ResponseEntity.ok().body(emailToken.get());
//        }else{
//            return ResponseEntity.ok().body("there is no token please try again");
//        }
//
//    }
    @Operation(summary = "유저 로그인 메서드 예제", description = "json 바디값을 통한 로그인 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "사용자가 정상 로그인됨", content = @Content(schema = @Schema(implementation = UserDto.SignInUserDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = UserDto.ResponseUserAfterLoginDto.class, message = "login", code=201)
    )
    @PostMapping("auth/login")//로그인을 위한 api 아이디와 비밀번호만을 가진 DTO 받음 Test pass
    public ResponseEntity<?> authenticate(@RequestBody UserDto.SignInUserDto signInUserDto){

        log.info("유저정보를 찾습니다");
        UserEntity userEntity = userService.getByCredentials( signInUserDto.getEmail(), signInUserDto.getPassword(), passwordEncoder);//인코더를 사용해서 저장
        userService.validateEmailAuthentication(userEntity.getEmail());// 이메일 인증 사용자 검증

        if(userEntity != null){

            log.info("토큰을 발급합니다");

            final String token = "Bearer :" + tokenProvider.create(userMapper.userEntityToJwtRequest(userEntity));// 수정 완료

            UserDto.ResponseUserAfterLoginDto responseUserAfterLoginDto = userMapper.userEntityToResponseUserAfterLogin(userEntity);
            responseUserAfterLoginDto.setToken(token);
            return ResponseEntity.ok().body(responseUserAfterLoginDto);

        }else{
            log.error("로그인을 할 수 없습니다!!");
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }



    @Operation(summary = "유저 등록 메서드 예제", description = "json 바디값을 통한 회원가입 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "201" ,description = "사용자가 정상 등록됨", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = UserEntity.class, message = "created", code=201)
    )
    @Transactional
    @PostMapping("auth/sighup")// 유저 등록 test pass

    public ResponseEntity postUserEntity(@Parameter(description = "POST DTO", required = true, example = "문서 참고")
                                             @ModelAttribute PostUserDto postUserDto) throws IOException {
       log.info("신규 유저를 등록합니다.");
        postUserDto.setPassword(passwordEncoder.encode(postUserDto.getPassword()));//password encode 적용
        UserEntity postingUserEntity= userMapper.userPostDtoToUserEntity(postUserDto);
        boolean test = postUserDto.getMultipartFileList().isEmpty();
        if(test){

            String defaultProfileImgUri = s3FileService.findByName("기본 프로필 이미지.jpg");
            S3ImageInfo profileImg  = S3ImageInfo.builder().fileName("기본 프로필 이미지.jpg").fileURI(defaultProfileImgUri).build();
            postingUserEntity.setProfileImg(profileImg);
        }
        else{
            List<S3ImageInfo> newProfileImg = s3FileService.uploadMultiFileList(postUserDto.getMultipartFileList());
            postingUserEntity.setProfileImg(newProfileImg.get(0));
        }

        postingUserEntity.getProfileImg().addUserEntity(postingUserEntity);
        if(emailTokenService.checkIsEmailTokenConfirmed(postUserDto.getEmailToken()).isExpired()){//인증이 된 이메일이라면
            postingUserEntity.setEmailVerified(true);
        }
        else throw  new BusinessLogicException(ExceptionCode.EMAIL_AUTHENTICATION_NEED);

        UserEntity postedUserEntity = userService.createUserEntity(postingUserEntity);

        UserDto.ResponseUserDto2 responseUserDto2 = userMapper.userEntityToResponseDto2(postedUserEntity);
        /*
        String emailToken = emailTokenService.createEmailToken(postedUserEntity.getEmail());// 이메일 인증 문자열을 만들고 전송하는 메소드

        responseUserDto2.setEmailToken(emailToken);//프론트 엔드에게 email token 값을 전송하기 위해서 사용함
        */


        log.info("프론트엔드에게 이메일 인증 문자열을 전송합니다");


        return new ResponseEntity(responseUserDto2, HttpStatus.CREATED);

    }

    @Operation(summary = "유저 정보 수정 메서드 예제", description = "json 바디값을 통한 회원 정보 수정 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자가 정상적으로 수정되었습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @io.swagger.annotations.ApiResponses(
            @io.swagger.annotations.ApiResponse(
                    response = UserDto.ResponseUserDto.class, message = "ok", code=200)
    )
    @PatchMapping("/patch")//유저 정보 수정
    public ResponseEntity patchUserEntity(@RequestBody UserDto.PatchUserDto patchUserDto){
        log.info("기존 유저 정보를 수정합니다.");

        UserEntity editingUserEntity= userMapper.userPatchDtoToUserEntity(patchUserDto);
        UserEntity editedUserEntity = userService.updateUserEntity(editingUserEntity);
        editingUserEntity.setDisplayName(editingUserEntity.getDisplayName()+"TEST");

        return new ResponseEntity( userMapper.userEntityToResponseDto2(editedUserEntity), HttpStatus.OK);

    }

    @Operation(summary = "유저 정보 요청 메서드 예제", description = "json 바디값을 통한 회원 정보 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자 정보를 불러왔습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/{userid}")//유저 정보(1인) 요청
    public ResponseEntity getUserEntity(@PathVariable(value = "userid") Long userId){

        UserEntity foundUserEntity = userService.findUserEntityById(userId);

        return new ResponseEntity( userMapper.userEntityToResponseDto2(foundUserEntity), HttpStatus.OK);
    }


    @Operation(summary = "유저 정보 요청 삭제 메서드 예제", description = "json 바디값을 통한 회원 정보 삭제 요청 메서드")//대상 api의 대한 설명을 작성하는 어노테이션
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자 정보를 삭제했습니다 ", content = @Content(schema = @Schema(implementation = UserDto.ResponseUserDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @DeleteMapping("/withdrawal")//기존 유저의 탈퇴 신청(DB 삭제X, DB 이전)
    public ResponseEntity withdrawalUserEntity(@RequestBody UserDto.DeleteUserDto deleteUserDto){
        log.info("기존 유저를 탈퇴처리합니다.");

        UserEntity deletingUserEntity= userMapper.userDeleteDtoToUserEntity(deleteUserDto);
        userService.withdrawalUser(deletingUserEntity);//DB 삭제가 아닌 유저 테이블 정보를 Withdrawal 테이블로 이전

        return new ResponseEntity(HttpStatus.OK);
    }


    @PatchMapping("/deactivate")//기존 유저의 탈퇴 신청(DB 삭제X, DB 이전)
    public ResponseEntity deactivateUserEntity(){
        log.info("활동 기간이 2년이 넘은 회원들을 비활동 처리합니다.");

        userService.deActiveCheckAllUsers();// 기준일마다 조건(2년)에 맞춰 유저 비활성화
        log.info("활동 기간이 2년이 넘은 회원들을 비활동 처리했습니다.");
        return new ResponseEntity(HttpStatus.OK);
    }


    @Operation(summary = "유저 팔로우 목록 호출 메서드 예제", description = "json 바디값을 통한 팔로우 목록 요청 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자의 팔로우 목록을 정상적으로 가져왔습니다  ", content = @Content(schema = @Schema(allOf = UserDto.FollowUserInfoResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/following")//내가 팔로우하는 사람의 목록 반환
    public ResponseEntity getUserFollowList(@RequestParam Long myUserEntityId, @RequestParam Integer pageIndex){
        Page<UserDto.FollowUserInfoResponseDto> foundUserFollowList = userService.getUserFollowList(myUserEntityId, pageIndex);

        return new ResponseEntity(
                new Multi_ResponseDTO<>(foundUserFollowList.getContent(), foundUserFollowList), //페이지 정보와 함께 복수 객체 반환
                HttpStatus.OK);

    }

    @Operation(summary = "유저 팔로워 목록 삭제 메서드 예제", description = "json 바디값을 통한 팔로워 목록 요청 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "200" ,description = "사용자의 팔로워 목록을 정상적으로 가져왔습니다  ", content = @Content(schema = @Schema(allOf = UserDto.FollowUserInfoResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")
    })
    @GetMapping("/get/follow")//나를 팔로우하는 사람의 목록 반환
    public ResponseEntity getUserFollowerList(@RequestParam Long myUserEntityId, @RequestParam int pageIndex){
        Page<UserDto.FollowUserInfoResponseDto> foundUserFollowList = userService.getUserFollowingList(myUserEntityId, pageIndex);

        return new ResponseEntity(
                new Multi_ResponseDTO<>(foundUserFollowList.getContent(), foundUserFollowList), //페이지 정보와 함께 복수 객체 반환
                HttpStatus.OK);
    }


    @DeleteMapping("/delete")// 유저 삭제(테스트용)
    public ResponseEntity deleteUser(@RequestParam Long myUserEntityId){
        UserEntity deletingUser  = userService.findUserEntityById(myUserEntityId);

        userService.realDeleteUser(deletingUser);
        log.info("유저가 삭제되었음");
        return new ResponseEntity( //페이지 정보와 함께 복수 객체 반환
                HttpStatus.OK);
    }


}
