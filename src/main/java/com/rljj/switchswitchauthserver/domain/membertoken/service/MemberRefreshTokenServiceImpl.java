package com.rljj.switchswitchauthserver.domain.membertoken.service;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberRefreshToken;
import com.rljj.switchswitchauthserver.domain.membertoken.repository.MemberRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class MemberRefreshTokenServiceImpl implements MemberRefreshTokenService {

    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

    @Value("${jwt.expired.refresh-token}")
    private long refreshTokenExpireTime;

    @Override
    public MemberRefreshToken createRefreshToken(Member member, String refreshToken) {
        return memberRefreshTokenRepository.save(MemberRefreshToken.builder()
                .member(member)
                .refreshToken(refreshToken)
                .expiredTime(refreshExpireTime())
                .build());
    }

    @Override
    public MemberRefreshToken getByMember(Member member) {
        return memberRefreshTokenRepository.findByMember(member);
    }

    @Override
    public void delete(Member member) {
        memberRefreshTokenRepository.deleteByMember(member);
    }

    private LocalDateTime refreshExpireTime() {
        return LocalDateTime.now().plus(refreshTokenExpireTime, ChronoUnit.MILLIS);
    }
}
