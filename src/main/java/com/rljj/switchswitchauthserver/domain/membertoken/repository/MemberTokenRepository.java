package com.rljj.switchswitchauthserver.domain.membertoken.repository;

import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {
}
