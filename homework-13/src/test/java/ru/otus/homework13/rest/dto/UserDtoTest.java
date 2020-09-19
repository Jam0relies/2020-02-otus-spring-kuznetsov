package ru.otus.homework13.rest.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.homework13.config.DtoMappingConfiguration;
import ru.otus.homework13.domain.Role;
import ru.otus.homework13.domain.User;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = DtoMappingConfiguration.class)
class UserDtoTest {
    @Autowired
    private ModelMapper modelMapper;

    @DisplayName("authorities should map to string")
    @Test
    void authoritiesMapping() {
        User user = new User();
        user.setAuthorities(new HashSet<>(Arrays.asList(new Role("user"), new Role("admin"))));
        UserDto mappedUser = modelMapper.map(user, UserDto.class);
        assertThat(mappedUser.getAuthorities()).containsExactlyInAnyOrder("user", "admin");
    }

}