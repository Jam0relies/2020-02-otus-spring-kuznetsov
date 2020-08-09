package ru.otus.homework07.repository;

import org.springframework.data.repository.query.Param;
import ru.otus.homework07.domain.Book;

public interface BookRepositoryCustom {
    Book addAuthorById(@Param("bookId") long bookId, @Param("authorId") long authorId);
    Book addGenreById(long bookId, long genreId);
}
