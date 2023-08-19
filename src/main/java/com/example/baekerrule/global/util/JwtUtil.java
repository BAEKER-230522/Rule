package com.example.baekerrule.global.util;

import com.example.baekerrule.error.exception.TokenValidException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private SecretKey cachedSecretKey;
    @Value("${custom.jwt.secret-key}")
    private String secretKeyPlain;

    private SecretKey _getSecretKey() {
        // 키를 Base64 인코딩함
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        // 인코딩된 키를 이용하여 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e) {
            throw new TokenValidException("토큰 만료 ");
        }catch (SignatureException e) {
            throw new TokenValidException("검증 되지않은 토큰");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return toMap(body);
    }

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    private static Map<String, Object> toMap(String jsonStr) {
        try {
            return new ObjectMapper().readValue(jsonStr, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}


