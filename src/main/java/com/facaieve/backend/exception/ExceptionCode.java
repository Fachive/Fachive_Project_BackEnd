package com.facaieve.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
    FORBIDDEN_WORD_USED(BAD_REQUEST, "금지된 단어가 사용되었습니다 다시 입력해주세요"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    NOT_FOLLOW(NOT_FOUND, "팔로우 중이지 않습니다"),
    NO_SUCH_ELEMENT(NOT_FOUND, "해당 요소는 존재하지 않습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_DISPLAY_NAME(CONFLICT, "해당 닉네임은 이미 존재합니다."),
    DUPLICATE_EMAIL(CONFLICT, "해당 이메일은 이미 존재합니다."),
    DORMANCY_DURATION_UNDER_2Y(CONFLICT, "휴면 기준인 2년에 맞지 않습니다."),
    PASSWORD_IS_WRONG(CONFLICT, "비밀번호가 잘못 입력되었습니다."),
    FILE_IS_NOT_EXIST_IN_BUCKET(CONFLICT, "해당 데이터가 존재하지 않습니다"),
    NONE_IMAGE_EXCEPTION(CONFLICT,"해당 이미지가 존재하지 않습니다"),
    REQUESTING_FILE_ALREADY_EXIST(CONFLICT,"해당 데이터가 이미 존재합니다");



    ;


    private final HttpStatus httpStatus;
    private final String message;
}
