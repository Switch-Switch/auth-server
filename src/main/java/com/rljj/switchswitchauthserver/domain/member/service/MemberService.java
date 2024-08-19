package com.rljj.switchswitchauthserver.domain.member.service;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {
    Optional<Member> getOpMemberByName(String name);

    Member getMemberByName(String name);

    Member getMember(Long id);
}
