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
    public static final String GRANT_TYPE = "Bearer ";
    private String accessToken;
    private String refreshToken;
}
