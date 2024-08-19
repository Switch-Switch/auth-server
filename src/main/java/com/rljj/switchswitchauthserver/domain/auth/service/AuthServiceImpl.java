package com.rljj.switchswitchauthserver.domain.auth.service;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchauthserver.domain.member.service.MemberService;
import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberToken;
import com.rljj.switchswitchauthserver.domain.membertoken.service.MemberTokenService;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtProvider;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtSet;
import com.rljj.switchswitchauthserver.global.config.security.CustomAuthenticationManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final CustomAuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final MemberTokenService memberTokenService;

    @Value("${jwt.expired.access-token}")
    private int accessTokenExpireTime;

    @Override
    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        JwtSet jwtSet = jwtProvider.generateTokenSet(authenticate.getName());
        // TODO refreshToken 저장 로직
        setCookie(jwtSet, response);
    }

    private void setCookie(JwtSet jwtSet, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", jwtSet.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(accessTokenExpireTime);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
