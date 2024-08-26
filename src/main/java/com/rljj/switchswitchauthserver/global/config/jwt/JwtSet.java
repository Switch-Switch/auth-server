package com.rljj.switchswitchauthserver.global.config.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class JwtSet {
    private String accessToken;
    private String refreshToken;
}
