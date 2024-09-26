package com.enzinior.sogo.auth.service;

import com.enzinior.sogo.auth.dto.CustomOAuth2User;
import com.enzinior.sogo.auth.dto.KakaoResponse;
import com.enzinior.sogo.auth.dto.NaverResponse;
import com.enzinior.sogo.auth.dto.OAuth2Response;
import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final NicknameService nicknameService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        switch(registrationId) {
            case "kakao":
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
                break;
            case "naver":
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                break;
            default:
                return null;
        }

        String id = oAuth2Response.getProviderId();
        boolean site = "kakao".equals(oAuth2Response.getProvider()) ? true : false;

        User existUser = userRepository.findBySocialId(id);

        String userUuid = UUID.randomUUID().toString();
        if(existUser == null) {
            User user = User.builder()
                    .socialId(id)
                    .nickname(nicknameService.createNickname())
                    .email(oAuth2Response.getEmail())
                    .site(site)
                    .role("ROLE_USER")
                    .userUuid(userUuid)
                    .sentence("안녕하세요")
                    .build();

            userRepository.save(user);

            UserDto.Auth auth = UserDto.Auth.builder()
                    .nickname(user.getNickname())
                    .role(user.getRole())
                    .userUuid(user.getUserUuid())
                    .build();

            return new CustomOAuth2User(auth);

        }

        else {
            UserDto.Auth auth = UserDto.Auth.builder()
                    .nickname(existUser.getNickname())
                    .role(existUser.getRole())
                    .userUuid(existUser.getUserUuid())
                    .build();

            return new CustomOAuth2User(auth);
        }

    }
}