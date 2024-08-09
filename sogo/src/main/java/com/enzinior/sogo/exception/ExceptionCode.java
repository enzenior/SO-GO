package com.enzinior.sogo.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    USER_NOT_FOUND(404, "User Not Found"),
    REVIEW_NOT_FOUND(404, "Review Not Found"),
    PLACE_NOT_FOUND(404, "Place Not Found"),
    COMMENT_NOT_FOUND(404, "Comment Not Found"),

    // 유저 관련
    NICKNAME_EXIST(409, "Nickname Already Exists"),
    EMAIL_EXIST(409, "Email Already Exists"),
    WITHDRAWAL_MEMBER(404, "접근이 불가능한 회원입니다."),

    // TOKEN 관련
    REFRESH_TOKEN_ERROR(401, "Invalid refresh token"),
    SIGNATURE_ERROR(401, "Token Signature Error"),
    RT_EXPIRED_ERROR(401, "RefreshToken Expired Error"),
    RT_NULL_ERROR(401, "RefreshToken Token Null"),
    AT_EXPIRED_ERROR(401, "AccessToken Expired Error"),
    ACCESS_TOKEN_ERROR(401, "AccessToken Error");

    private int status;

    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
