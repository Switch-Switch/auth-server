package com.rljj.switchswitchauthserver.domain.auth.service;

import com.rljj.switchswitchauthserver.domain.auth.dto.LoginRequest;
import com.rljj.switchswitchauthserver.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.member.service.MemberService;
import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberRefreshToken;
import com.rljj.switchswitchauthserver.domain.membertoken.service.MemberRefreshTokenService;
import com.rljj.switchswitchauthserver.global.config.exception.DuplicatedException;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtProvider;
import com.rljj.switchswitchauthserver.global.config.jwt.JwtSet;
import com.rljj.switchswitchauthserver.global.config.security.CustomAuthenticationManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final CustomAuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final MemberRefreshTokenService memberRefreshTokenService;
    private final MemberService memberService;

    @Override
    @Transactional
    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberService.getMemberByName(loginRequest.getName());
        authenticate(loginRequest);
        return handleJwt(response, member);
    }

    @Override
    @Transactional
    public String signup(SignupRequest signupRequest, HttpServletResponse response) {
        Optional<Member> member = memberService.getOpMemberByName(signupRequest.getName());
        if (member.isPresent()) {
            throw new DuplicatedException("Duplicated Member", signupRequest.getName());
        }
        return handleJwt(response, memberService.createMember(signupRequest));
    }

    private void authenticate(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    private String handleJwt(HttpServletResponse response, Member member) {
        JwtSet jwtSet = jwtProvider.generateTokenSet(member.getName());
        MemberRefreshToken memberRefreshToken = member.getMemberRefreshToken();
        if (memberRefreshToken != null) {
            memberRefreshToken.updateExpired();
        } else {
            member.setMemberRefreshToken(
                    memberRefreshTokenService.createRefreshToken(member, jwtSet.getRefreshToken()));
        }
        jwtProvider.setJwtInCookie(jwtSet.getAccessToken(), response);
        return jwtSet.getAccessToken();
    }
}
