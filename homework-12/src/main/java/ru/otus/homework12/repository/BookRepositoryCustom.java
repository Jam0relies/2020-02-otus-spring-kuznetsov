package ru.otus.homework12.repository;

import ru.otus.homework12.domain.Comment;

import java.util.List;

public interface BookRepositoryCustom {
    void removeAuthorFromBook(String bookId, String authorId);

    void addAuthorToBook(String bookId, String authorId);

    void removeGenreFromBook(String bookId, String genre);

    void addGenreToBook(String bookId, String genre);

    List<Comment> getAllComments(String bookId);

    void addComment(String bookId, Comment comment);
}
