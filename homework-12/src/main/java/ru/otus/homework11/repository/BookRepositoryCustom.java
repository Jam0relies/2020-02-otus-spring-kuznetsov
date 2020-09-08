package ru.otus.homework11.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Comment;

public interface BookRepositoryCustom {
    Mono<?> removeAuthorFromBook(String bookId, String authorId);

    Mono<?> addAuthorToBook(String bookId, String authorId);

    Mono<?> removeGenreFromBook(String bookId, String genre);

    Mono<?> addGenreToBook(String bookId, String genre);

    Flux<Comment> getAllComments(String bookId);

    Mono<?> addComment(String bookId, Comment comment);
}
