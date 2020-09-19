package ru.otus.homework13.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework13.config.JwtConfig;
import ru.otus.homework13.rest.dto.Token;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private final JwtConfig jwtConfig;

    public TokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Token generateAccessToken(String subject) {
        Date now = new Date();
        Long duration = now.getTime() + jwtConfig.getTokenExpirationMsec();
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSecret())
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public Token generateRefreshToken(String subject) {
        Date now = new Date();
        Long duration = now.getTime() + jwtConfig.getRefreshTokenExpirationMsec();
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSecret())
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getTokenSecret()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public LocalDateTime getExpiryDateFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getTokenSecret()).parseClaimsJws(token).getBody();
        return LocalDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    public boolean validateToken(@Nullable String token) {
        if (token == null) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(jwtConfig.getTokenSecret()).parse(token);
            return true;
        } catch (Exception e) {
            log.debug("Token validation error", e);
        }
        return false;
    }
}
