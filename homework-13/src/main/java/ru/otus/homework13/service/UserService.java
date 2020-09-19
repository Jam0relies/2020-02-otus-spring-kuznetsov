package ru.otus.homework13.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.homework13.domain.User;
import ru.otus.homework13.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('admin')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
