package com.enzinior.sogo.auth.service;

import com.enzinior.sogo.auth.repository.RefreshTokenRepository;
import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${spring.jwt.expiration}")
    private String expiration;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @Transactional
    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {

        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {
            throw new BusinessLogicException(ExceptionCode.RT_NULL_ERROR);
        }

        String username = jwtUtil.getUserUuid(refresh);
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            refreshTokenRepository.deleteByUser_UserUuid(username);
            throw new BusinessLogicException(ExceptionCode.RT_EXPIRED_ERROR);
        }


        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshTokenRepository.existsRefreshTokenByRefreshToken(refresh);
        if(!isExist) {
            throw new BusinessLogicException(ExceptionCode.RT_NOT_FOUND_ERROR);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_ERROR);
        }

        String role = jwtUtil.getRole(refresh);
        String newAccess = jwtUtil.createJwt("access", username, role, Long.parseLong(expiration));
        response.setHeader("Authorization", "Bearer " + newAccess);

    }
}
