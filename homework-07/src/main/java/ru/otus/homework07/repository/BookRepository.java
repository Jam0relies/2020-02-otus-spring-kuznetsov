package ru.otus.homework07.repository;

import ru.otus.homework07.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> findByName(String name);

    List<Book> findAll();

    boolean delete(long id);

    Book addAuthorById(long bookId, long authorId);

    Book addGenreById(long bookId, long genreId);
}
