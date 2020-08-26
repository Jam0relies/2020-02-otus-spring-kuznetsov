package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
