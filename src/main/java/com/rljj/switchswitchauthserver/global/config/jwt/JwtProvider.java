package com.rljj.switchswitchauthserver.global.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Value("${jwt.expired.refresh-token}")
    private long refreshTokenExpireTime;

    @Value("${jwt.secret}")
    private String secretKey;

    public JwtSet generateTokenSet(Long id) {
        return JwtSet.builder()
                .accessToken(generateToken(id, accessTokenExpireTime))
                .refreshToken(generateToken(id, refreshTokenExpireTime))
                .build();
    }

    public String generateToken(Long id, Long expired) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .expiration(new Date(System.currentTimeMillis() + expired))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public Long parseSubject(String jwt) {
        String id = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
        return Long.parseLong(id);
    }

    public boolean isExpired(String jwt) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }
}
