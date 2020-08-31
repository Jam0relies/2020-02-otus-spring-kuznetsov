package ru.otus.homework11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework11.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
