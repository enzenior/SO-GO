package com.enzinior.sogo.auth.dto;

public interface OAuth2Response {

    String getProvider();

    // 서비스에서 제공하는 사용자 아이디
    String getProviderId();

    // 이메일 반환
    String getEmail();

    // 사용자가 설정한 이름 반환
    String getName();

    // 사용자가 설정한 닉네임 반환
    String getNickname();
}
