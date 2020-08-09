package ru.otus.homework07.repository;

import ru.otus.homework07.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(long id);

    long count();

    Author save(Author genre);

    List<Author> findByName(String name);

    List<Author> findAll();

    boolean delete(long id);
}
