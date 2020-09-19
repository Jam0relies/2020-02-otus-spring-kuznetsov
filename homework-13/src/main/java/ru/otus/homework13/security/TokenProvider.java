package ru.otus.homework13.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.homework13.config.JwtConfig;
import ru.otus.homework13.rest.dto.Token;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenProvider {
    private final JwtConfig jwtConfig;
    private final ModelMapper modelMapper;

    public TokenProvider(JwtConfig jwtConfig, ModelMapper modelMapper) {
        this.jwtConfig = jwtConfig;
        this.modelMapper = modelMapper;
    }

    public Token generateAccessToken(UserDetails userDetails) {
        Date now = new Date();
        Long duration = now.getTime() + jwtConfig.getTokenExpirationMsec();
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSecret())
                .compact();
        return new Token(Token.TokenType.ACCESS, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public Token generateRefreshToken(String username) {
        Date now = new Date();
        Long duration = now.getTime() + jwtConfig.getRefreshTokenExpirationMsec();
        Date expiryDate = new Date(duration);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getTokenSecret())
                .compact();
        return new Token(Token.TokenType.REFRESH, token, duration, LocalDateTime.ofInstant(expiryDate.toInstant(), ZoneId.systemDefault()));
    }

    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getTokenSecret()).parseClaimsJws(token).getBody();
        Set<JwtAuthority> list = (Set<JwtAuthority>) claims.get("authorities", ArrayList.class).stream()
                .map(map -> modelMapper.map(map, JwtAuthority.class))
                .collect(Collectors.toSet());
        log.info("{}", list);
        return new JwtUserDetails(claims.getSubject(), list);
    }

    public static class AuthoritySet extends ArrayList<GrantedAuthority> {

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
