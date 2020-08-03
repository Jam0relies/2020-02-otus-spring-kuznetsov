package ru.otus.homework06.repository;

import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Genre;

import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findById(long id);
}
