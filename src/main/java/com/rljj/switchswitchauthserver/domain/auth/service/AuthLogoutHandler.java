package com.rljj.switchswitchauthserver.domain.auth.service;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.member.service.MemberService;
import com.rljj.switchswitchauthserver.domain.membertoken.service.MemberRefreshTokenService;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthLogoutHandler implements LogoutHandler {

    private final JwtProvider jwtProvider;
    private final MemberRefreshTokenService memberRefreshTokenService;
    private final MemberService memberService;

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Member member = memberService.getMemberByName(jwtProvider.parseSubject(request));
        member.setMemberRefreshToken(null);
        memberRefreshTokenService.delete(member);
        jwtProvider.expireJwtInCookie(response);
    }
}
