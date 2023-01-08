package com.facaieve.backend.dto;


import com.facaieve.backend.entity.user.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.*;


@Getter
@Setter
public class UserDto {

    @Getter
    @Schema(description = "회원 등록 DTO")
    @NoArgsConstructor
    public static class PostUserDto{

    @Schema(description = "유저 닉네임")
    String displayName;

    @Email
    @Schema(description = "유저 이메일")
    String email;
    @Schema(description = "비밀번호")
    String password;
    @Schema(description = "시,도")
    String state;
    @Schema(description = "시군구")
    String city;
    @Schema(description = "간단한 자기소개")
    String userInfo;
    @Schema(description = "커리어")
    String career;
    @Schema(description = "학력 및 교육사항")
    String education;
    @Schema(description = "재직회사")
    String Company;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "회원 정보 수정 DTO")
    public static class PatchUserDto{

        @Schema(description = "사용자 활동명")
        long userEntityId;
        @Schema(description = "사용자 활동명")
        String displayName;

        @Email
        @Schema(description = "유저 이메일")
        String email;
        @Schema(description = "시,도")
        String state;
        @Schema(description = "시군구")
        String city;
        @Schema(description = "간단한 자기소개")
        String userInfo;
        @Schema(description = "커리어")
        String career;
        @Schema(description = "학력 및 교육사항")
        String education;
        @Schema(description = "재직회사")
        String Company;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "회원 정보 요청 DTO")
    public static class GetUserDto{
        @Schema(description = "유저 식별ID")
        long userEntityId;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "회원 삭제 DTO")
    public static class DeleteUserDto{

        @Schema(description = "유저 식별ID")
        long UserEntityId;
        @Email
        @Schema(description = "유저 이메일")
        String email;
        @Schema(description = "비밀번호")
        String password;

    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "회원 Response")
    public static class ResponseUserDto{

        @Schema(description = "유저 닉네임")
        String displayName;

        @Email
        @Schema(description = "유저 이메일")
        String email;

        public static ResponseUserDto of(UserEntity userEntity) {
            return new ResponseUserDto(userEntity.getDisplayName(), userEntity.getEmail());
        }
    }




}
