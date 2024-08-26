package com.rljj.switchswitchauthserver.global.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtProviderTest {

    @Autowired
    public JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expired.refresh-token}")
    private Long expired;

    @Test
    public void testGenerateJwt() {
        String jwt = jwtProvider.generateToken("1", expired);
        assertThat(jwt).isNotBlank();
    }

    @Test
    public void testGenerateSecretKey() {
        byte[] keyBytes = Base64.getUrlDecoder().decode(secret);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        System.out.println(secretKey);
    }

    @Test
    public void testJWT() {
        //given
        String givenId = "1";
        JwtSet jwtSet = jwtProvider.generateTokenSet(givenId);

        //when
        String resultId = Jwts.parser()
                .verifyWith((SecretKey) jwtProvider.getSecretKey())
                .build()
                .parseSignedClaims(jwtSet.getAccessToken())
                .getPayload()
                .getSubject();

        //then
        assertThat(Long.parseLong(resultId)).isEqualTo(givenId);
    }
}
