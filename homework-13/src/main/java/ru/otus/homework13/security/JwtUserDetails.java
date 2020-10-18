package ru.otus.homework13.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetails {
    private final String username;
    private final String password = null;
    private final Set<JwtAuthority> authorities;
    private final boolean accountNonExpired = true;
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean enabled = true;
}
