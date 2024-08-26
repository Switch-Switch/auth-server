package com.rljj.switchswitchauthserver.domain.member.entity;

import com.rljj.switchswitchauthserver.domain.membertoken.entity.MemberRefreshToken;
import com.rljj.switchswitchauthserver.global.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    private MemberRefreshToken memberRefreshToken;
}
