package ru.otus.homework13.rest;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework13.rest.dto.UserDto;
import ru.otus.homework13.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public UserDto getUserInfo() {
        return modelMapper.map(userService.getUserDetails(), UserDto.class);
    }

    @GetMapping("/api/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream().map(u -> modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
    }
}
