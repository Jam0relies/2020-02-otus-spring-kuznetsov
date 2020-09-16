package ru.otus.homework13.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @DisplayName("builder fill security status fields")
    @Test
    void builderTest() {
        User user = User.builder().build();

        assertThat(user).extracting("accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled")
                .allMatch(b -> (Boolean) b);
    }

    @DisplayName("no args constructor fill security status fields")
    @Test
    void defaultConstructorTest() {
        User user = new User();
        assertThat(user).extracting("accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled")
                .allMatch(b -> (Boolean) b);
    }
}