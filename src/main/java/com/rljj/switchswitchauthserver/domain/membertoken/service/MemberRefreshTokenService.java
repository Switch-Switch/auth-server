package com.rljj.switchswitchauthserver.domain.membertoken.service;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberRefreshToken;

public interface MemberRefreshTokenService {
    MemberRefreshToken createRefreshToken(Member member, String refreshToken);

    MemberRefreshToken getByMember(Member member);

    void delete(Member member);
}
