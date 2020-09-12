package ru.otus.homework12.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.homework12.domain.User;
import ru.otus.homework12.mongock.MongockConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User repository test")
@DataMongoTest
@Import(MongockConfig.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void findAllUserDetailsByUsername() {
        final String expectedUsername = "SomeUsername";
        final String expectedPassword = "Correct Horse Battery Staple";
        User user = User.builder()
                .username(expectedUsername)
                .password(expectedPassword)
                .build();
        mongoTemplate.save(user, "users");
        UserDetails actualUserDetails = userRepository.findUserDetailsByUsername(expectedUsername).get();
        assertThat(actualUserDetails.getUsername()).isEqualTo(expectedUsername);
        assertThat(actualUserDetails.getPassword()).isEqualTo(expectedPassword);
        assertThat(actualUserDetails).extracting("accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled")
                .allMatch(b -> (Boolean) b);
    }

    @Test
    void notEmptyUsername() {
        User user = User.builder().build();
        userRepository.save(user);
    }
}