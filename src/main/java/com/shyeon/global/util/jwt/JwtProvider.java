package com.shyeon.global.util.jwt;

import com.shyeon.global.exception.customexception.TokenCustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Getter
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Getter
    @Value("${jwt.token.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    private Key key;

    @PostConstruct
    public void init() {
        String base64EncodedSecretKey = encodeBase64SecretKey(this.secretKey);
        this.key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
    }

    // SecretKey Base64로 Encode
    private String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Access Token 발급
    public String generateAccessToken(String email) {
        Date accessTokenExpiresIn = getTokenExpiration(accessTokenExpirationMillis);

        // 이메일 값으로 Claims 생성
        Claims claims = Jwts.claims().setSubject(email);

        String accessToken =
                Jwts.builder()
                        // Claims 셋팅
                        .setClaims(claims)
                        // 시작시간
                        .setIssuedAt(Calendar.getInstance().getTime())
                        // 만료시간
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(SignatureAlgorithm.HS256, key)
                        .compact();

        return accessToken;
    }

    // 토큰 만료시간 가져오기
    private Date getTokenExpiration(long expirationMillisecond) {
        Date date = new Date();
        return new Date(date.getTime() + expirationMillisecond);
    }

    // Request Header 에서 Access Token 값 추출
    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Access Token 검증
    public Claims validateToken(String token) {
        try {
            return parseClaims(token);
        } catch (MalformedJwtException e) {
            throw TokenCustomException.MALFORMED_TOKEN;
        } catch (UnsupportedJwtException e) {
            throw TokenCustomException.WRONG_TYPE_TOKEN;
        } catch (SignatureException e) {
            throw TokenCustomException.WRONG_TYPE_SIGNATURE;
        } catch (ExpiredJwtException e) {
            throw TokenCustomException.EXPIRED_TOKEN;
        } catch (IllegalArgumentException e) {
            throw TokenCustomException.DOES_NOT_EXIST_TOKEN;
        }
    }

    // token parse
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
