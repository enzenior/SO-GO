package com.enzinior.sogo.auth.handler;

import com.enzinior.sogo.auth.dto.CustomOAuth2User;
import com.enzinior.sogo.auth.entity.RefreshToken;
import com.enzinior.sogo.auth.repository.RefreshTokenRepository;
import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.jwt.JwtUtil;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.jwt.refresh-token.expiration}")
    private String expiration;
    @Value("${client.url}")
    private String clientUrl;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // OAuth2User
        OAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String userUuid = customUserDetails.getName();
        String refresh = jwtUtil.createJwt("refresh", userUuid, role, Long.parseLong(expiration));

        User user = userService.findUser(userUuid);

        String expiredTime = new Date(System.currentTimeMillis() + Long.parseLong(expiration)).toString();

        RefreshToken refreshToken = refreshTokenRepository.findByUserUserUuid(userUuid)
                .orElse(new RefreshToken(refresh, expiredTime, user));

        refreshTokenRepository.save(refreshToken);

        ResponseCookie cookie = createCookie("refresh", refresh);
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        System.out.println("Created Cookie: " + cookie.toString());
        response.sendRedirect("https://" + clientUrl + "/loading");
    }

    private ResponseCookie createCookie(String key, String value) {
        return ResponseCookie.from(key, value)
                .domain(null)
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();
    }
}
