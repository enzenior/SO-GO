package com.enzinior.sogo.auth.service;

import com.enzinior.sogo.auth.repository.RefreshTokenRepository;
import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.jwt.expiration}")
    private long jwtExpiration;

    @Override
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

        if(jwtUtil.isExpired(refresh)) {
            refreshTokenRepository.deleteByRefreshToken(refresh);
            throw new BusinessLogicException(ExceptionCode.RT_EXPIRED_ERROR);
        }

        String category = jwtUtil.getCategory(refresh);

        // DB에 저장되어 있는지 확인
        Boolean isExist = refreshTokenRepository.existsRefreshTokenByRefreshToken(refresh);

        if(!isExist) {
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_ERROR);
        }

        if (!category.equals("refresh")) {
            throw new BusinessLogicException(ExceptionCode.REFRESH_TOKEN_ERROR);
        }

        String role = jwtUtil.getRole(refresh);
        String username = jwtUtil.getUserUuid(refresh);
        String newAccess = jwtUtil.createJwt("access", username, role, jwtExpiration);

        response.setHeader("Authorization", "Bearer " + newAccess);
    }
}
