package ru.otus.homework13.rest;

import org.apache.maven.artifact.repository.Authentication;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.rest.dto.UserDto;

@RestController
public class UserController {
    private final ModelMapper modelMapper;

    public UserController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/user")
    public UserDto getUserInfo(Authentication authentication) {
        return modelMapper.map(authentication, UserDto.class);
    }
}
