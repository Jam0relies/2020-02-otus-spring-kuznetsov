package ru.otus.homework13.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.rest.dto.LoginRequestDto;
import ru.otus.homework13.rest.dto.LoginResponseDto;
import ru.otus.homework13.security.AuthService;

import javax.validation.Valid;

@Slf4j
@RestController
public class SecurityController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public SecurityController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<LoginResponseDto> login(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @Valid @RequestBody LoginRequestDto loginRequest) {
        log.debug("Login request for {}", loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authService.login(loginRequest, accessToken, refreshToken);
    }

    /**
     * For authentication check and future JWT token refreshing
     */
    @PostMapping("/api/auth/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@CookieValue(name = "accessToken", required = false) String accessToken,
                                                         @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return authService.refresh(accessToken, refreshToken);
    }
}
