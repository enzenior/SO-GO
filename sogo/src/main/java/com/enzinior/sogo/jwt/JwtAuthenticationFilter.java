package com.enzinior.sogo.jwt;

import com.enzinior.sogo.auth.dto.CustomOAuth2User;
import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.user.dto.UserDto;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtUtil.extractToken(request.getHeader("Authorization"));

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtUtil.isValidAccessToken(accessToken)) {
                setAuthenticationToContext(accessToken);
            } else {
                SecurityContextHolder.clearContext();
                throw new BusinessLogicException(ExceptionCode.AT_EXPIRED_ERROR);
            }
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            throw new BusinessLogicException((ExceptionCode.ACCESS_TOKEN_ERROR));
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(String accessToken) {
        String userUuid = jwtUtil.getUserUuid(accessToken);
        String role = jwtUtil.getRole(accessToken);

        UserDto.Auth user = UserDto.Auth.builder()
                .userUuid(userUuid)
                .role(role)
                .build();

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/login") || path.startsWith("/oauth");
    }
}
