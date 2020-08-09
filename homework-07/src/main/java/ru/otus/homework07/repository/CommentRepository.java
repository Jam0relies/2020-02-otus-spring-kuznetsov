package ru.otus.homework07.repository;

import ru.otus.homework07.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

    List<Comment> findAll();

    boolean delete(long id);
}
