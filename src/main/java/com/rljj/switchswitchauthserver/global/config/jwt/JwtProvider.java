package com.rljj.switchswitchauthserver.global.config.jwt;

import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;

public interface JwtProvider {
    JwtSet generateTokenSet(String name);

    String generateToken(String subject, Long expired);

    SecretKey getSecretKey();

    String parseSubject(String jwt);

    /**
     * 만료 여부와 관계없이 JWT의 subject를 가져옴, 필요할 때만 사용하고 이외에는 parseSubject() 사용
     * @param jwt accessToken
     * @return subject (사용자 이름, name)
     */
    String parseSubjectWithoutSecure(String jwt);

    boolean isExpired(String jwt);

    void setJwtInCookie(String accessToken, HttpServletResponse response);

    String refreshAuthorization(String jwt, HttpServletResponse response);
}
