package com.rljj.switchswitchauthserver.domain.member.service;

import com.rljj.switchswitchauthserver.domain.auth.dto.SignupRequest;
import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.member.repository.MemberRepository;
import com.rljj.switchswitchauthserver.global.config.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Override
    public Optional<Member> getOpMemberByName(String name) {
        return memberRepository.findByName(name);
    }

    @Override
    public Member getMemberByName(String name) {
        return getOpMemberByName(name).orElseThrow(() -> new UserNotFoundException(name));
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    @Override
    public Member createMember(SignupRequest signupRequest) {
        return memberRepository.save(Member.builder()
                .name(signupRequest.getName())
                .password(encoder.encode(signupRequest.getPassword()))
                .build());
    }
}
