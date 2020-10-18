package ru.otus.homework13.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.homework13.rest.dto.LoginRequestDto;
import ru.otus.homework13.rest.dto.LoginResponseDto;
import ru.otus.homework13.rest.dto.Token;

@Slf4j
@Service
public class AuthService {
    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final CookieAccess cookieAccess;

    public AuthService(TokenProvider tokenProvider, UserDetailsService userDetailsService, CookieAccess cookieAccess) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.cookieAccess = cookieAccess;
    }


    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest, String accessToken, String refreshToken) {
        String login = loginRequest.getUsername();
        UserDetails user = userDetailsService.loadUserByUsername(login);

        boolean accessTokenValid = tokenProvider.validateToken(accessToken);
        boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

        HttpHeaders responseHeaders = new HttpHeaders();
        Token newAccessToken;
        Token newRefreshToken;
        if (!accessTokenValid && !refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user);
            newRefreshToken = tokenProvider.generateRefreshToken(user.getUsername());
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        if (!accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user);
            addAccessTokenCookie(responseHeaders, newAccessToken);
        }

        if (accessTokenValid && refreshTokenValid) {
            newAccessToken = tokenProvider.generateAccessToken(user);
            newRefreshToken = tokenProvider.generateRefreshToken(user.getUsername());
            addAccessTokenCookie(responseHeaders, newAccessToken);
            addRefreshTokenCookie(responseHeaders, newRefreshToken);
        }

        LoginResponseDto loginResponse = new LoginResponseDto(LoginResponseDto.SuccessFailure.SUCCESS, "Auth successful. Tokens are created in cookie.");
        log.debug("authenticated");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);

    }


    public ResponseEntity<LoginResponseDto> refresh(String refreshToken) {
        Boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);
        if (!refreshTokenValid) {
            throw new IllegalArgumentException("Refresh Token is invalid!");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Token newAccessToken = tokenProvider.generateAccessToken(userDetails);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, cookieAccess.createAccessTokenCookie(newAccessToken.getTokenValue(), newAccessToken.getDuration()).toString());

        LoginResponseDto loginResponse = new LoginResponseDto(LoginResponseDto.SuccessFailure.SUCCESS, "Auth successful. Tokens are created in cookie.");
        return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
    }

    private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieAccess.createAccessTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }

    private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
        httpHeaders.add(HttpHeaders.SET_COOKIE, cookieAccess.createRefreshTokenCookie(token.getTokenValue(), token.getDuration()).toString());
    }
}
