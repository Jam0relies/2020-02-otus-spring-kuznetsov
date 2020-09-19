package ru.otus.homework13.security;

import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import ru.otus.homework13.config.JwtConfig;

@Component
public class CookieAccess {
    private final JwtConfig jwtConfig;

    public CookieAccess(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public HttpCookie createAccessTokenCookie(String token, Long duration) {
        return ResponseCookie.from(jwtConfig.getAccessTokenCookieName(), token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                // TODO: enable secure cookie for HTTPS
                //.secure(true)
                .build();
    }

    public HttpCookie createRefreshTokenCookie(String token, Long duration) {
        return ResponseCookie.from(jwtConfig.getRefreshTokenCookieName(), token)
                .maxAge(duration)
                .httpOnly(true)
                .path("/api/auth/refresh")
                .sameSite("Strict")
                // TODO: enable secure cookie for HTTPS
                //.secure(true)
                .build();
    }

    public HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(jwtConfig.getAccessTokenCookieName(), "").maxAge(0).httpOnly(true).path("/").build();
    }
}
