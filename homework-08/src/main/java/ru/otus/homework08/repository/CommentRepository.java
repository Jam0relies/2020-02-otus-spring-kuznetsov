package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
}
