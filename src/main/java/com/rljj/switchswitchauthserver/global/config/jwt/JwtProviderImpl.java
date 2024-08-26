package com.rljj.switchswitchauthserver.global.config.jwt;

import com.rljj.switchswitchauthserver.domain.member.entity.Member;
import com.rljj.switchswitchauthserver.domain.member.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${jwt.expired.access-token}")
    private long accessTokenExpireTime;

    @Value("${jwt.expired.refresh-token}")
    private long refreshTokenExpireTime;

    @Value("${JWT.SECRET}")
    private String secretKey;

    private final String TOKEN_KEY = "jwt";

    public final String GRANT_TYPE = "Bearer ";


    private final MemberService memberService;

    @Override
    public JwtSet generateTokenSet(String name) {
        return JwtSet.builder()
                .accessToken(generateToken(name, accessTokenExpireTime))
                .refreshToken(generateToken(name, refreshTokenExpireTime))
                .build();
    }

    @Override
    public String generateToken(String subject, Long expired) {
        return Jwts.builder()
                .subject(subject)
                .expiration(new Date(System.currentTimeMillis() + expired))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    @Override
    public String parseSubject(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    @Override
    public String parseSubject(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith(GRANT_TYPE)) {
            return null;
        }
        return parseSubject(authHeader.substring(7));
    }

    @Override
    public String parseSubjectWithoutSecure(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }

    @Override
    public boolean isExpired(String jwt) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    @Override
    public void setJwtInCookie(String accessToken, HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_KEY, accessToken);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) accessTokenExpireTime);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    @Transactional
    public String refreshAuthorization(String accessToken, HttpServletResponse response) {
        String username = parseSubjectWithoutSecure(accessToken);
        Member member = memberService.getMemberByName(username);
        String refreshToken = member.getMemberRefreshToken().getRefreshToken();
        if (isExpired(refreshToken)) { // 재로그인
            throw new BadCredentialsException("Refresh token expired");
        }
        accessToken = generateToken(member.getName(), accessTokenExpireTime);
        setJwtInCookie(accessToken, response);
        return accessToken;
    }

    @Override
    public void expireJwtInCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(TOKEN_KEY, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
