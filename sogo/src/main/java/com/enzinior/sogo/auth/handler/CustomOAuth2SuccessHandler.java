package com.enzinior.sogo.auth.handler;

import com.enzinior.sogo.auth.dto.CustomOAuth2User;
import com.enzinior.sogo.auth.entity.RefreshToken;
import com.enzinior.sogo.auth.repository.RefreshTokenRepository;
import com.enzinior.sogo.jwt.JwtUtil;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        OAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String userUuid = customUserDetails.getName();
        String refresh = jwtUtil.createJwt("refresh", userUuid, 86400000L);

        addRefreshEntity(userUuid, refresh, 864000L);

        response.addCookie(createCookie("refresh", refresh));
        response.sendRedirect("http://localhost:3000/");
    }

    private void addRefreshEntity(String userUuid, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);
        User user = userRepository.findByUserUuid(userUuid).get();

        RefreshToken refreshEntity = RefreshToken.builder()
                .refreshToken(refresh)
                .expiration(date.toString())
                .user(user)
                .build();

        refreshTokenRepository.save(refreshEntity);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(60 * 60 * 60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
