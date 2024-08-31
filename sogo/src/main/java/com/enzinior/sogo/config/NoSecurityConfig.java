package com.enzinior.sogo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.enzinior.sogo.auth.handler.CustomOAuth2SuccessHandler;
import com.enzinior.sogo.auth.repository.CustomClientRegistrationRepo;
import com.enzinior.sogo.auth.repository.RefreshTokenRepository;
import com.enzinior.sogo.auth.service.CustomOAuth2UserService;
import com.enzinior.sogo.jwt.CustomLogoutFilter;
import com.enzinior.sogo.jwt.JwtAuthenticationFilter;
import com.enzinior.sogo.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("local")
public class NoSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().permitAll() //.authenticated()
                );
        return http.build();
    }

}
