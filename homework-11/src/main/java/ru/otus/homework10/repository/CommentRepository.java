package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
