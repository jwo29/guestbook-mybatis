package com.january.guestbook.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

// 스프링 환경이 아닌 곳에서 사용할 수 있도록 간단한 유티리티 클래스로 설계
@Log4j2
public class JWTUtil {

    private String secretKey = "guestbook12345678guestbook12345678";

    // 유효기간: 한 달(minutes)
    private long expire = 60 * 24 * 30;

    private final SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    // JWT 토큰 생성
    public String generateToken(String content) throws Exception{

        return Jwts.builder()
                .issuedAt(new Date())
//                .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusSeconds(1).toInstant()))
                .subject(content)
                .signWith(key)
                .compact();

    }

    // JWT 검증 - 인코딩된 문자열에서 원하는 값을 추출한다
    public String validateAndExtract(String token) throws Exception{
        String contentValue = null;

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            log.info("Claims received: " + claims);
            log.info("---------------------");

            contentValue = claims.getSubject();
        } catch (Exception e) {
            log.error(e.getMessage());
            contentValue = null;
        }

        return contentValue;
    }
}
