package ru.otus.homework13.rest;

import org.apache.maven.artifact.repository.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.domain.User;
import ru.otus.homework13.rest.dto.UserDto;
import ru.otus.homework13.service.UserService;

import java.util.List;

@RestController
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public UserDto getUserInfo(Authentication authentication) {
        return modelMapper.map(authentication, UserDto.class);
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
