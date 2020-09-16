package ru.otus.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.homework13.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<UserDetails> findUserDetailsByUsername(String username);
}
