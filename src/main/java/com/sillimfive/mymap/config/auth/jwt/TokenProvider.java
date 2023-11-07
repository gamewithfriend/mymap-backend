package com.sillimfive.mymap.config.auth.jwt;

import com.sillimfive.mymap.domain.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private byte[] keyHash;

    /**
     * Secret Key를 SHA-256알고리즘으로 해싱처리한다.
     */
    @PostConstruct
    private void init() {
        try {
            keyHash = MessageDigest.getInstance("SHA-256").digest(jwtProperties.getSecretKey().getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, keyHash)
                .compact();
    }

    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(keyHash)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Authentication getAuthentication(String token) {
        //비밀키를 사용하여 복호화한 클레임을 가져오는 메서드 실행
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        // todo: refactoring
        User user = User.builder()
                .email(claims.getSubject())
                .id(claims.get("id",Long.class))
                .build();

        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()//클레임 조회
                .setSigningKey(keyHash)
                .parseClaimsJws(token)
                .getBody();
    }
}
