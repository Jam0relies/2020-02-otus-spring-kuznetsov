package ru.otus.homework11.repository;

import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {
    Mono<?> removeAuthorFromBook(String bookId, String authorId);

    Mono<?> addAuthorToBook(String bookId, String authorId);

    Mono<?> removeGenreFromBook(String bookId, String genre);

    Mono<?> addGenreToBook(String bookId, String genre);
}
