package ru.otus.homework13.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() throws Exception {
        return new InMemoryUserDetailsManager();
    }
}
