package com.rljj.switchswitchauthserver.domain.membertoken.entity;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.global.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MemberRefreshToken extends BaseEntity {

    @JoinColumn(nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime expiredTime;

    @Transient
    @Value("${jwt.expired.refresh-token}")
    private long refreshTokenExpireTime;

    public void updateExpired() {
        this.expiredTime = LocalDateTime.now().plus(refreshTokenExpireTime, ChronoUnit.MILLIS);
    }
}
