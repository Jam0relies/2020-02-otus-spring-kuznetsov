package ru.otus.homework13.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("security.auth")
@Configuration
public class JwtConfig {
    private String tokenSecret;
    private long tokenExpirationMsec;
    private long refreshTokenExpirationMsec;
    private String accessTokenCookieName;
    private String refreshTokenCookieName;
}
