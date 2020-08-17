package ru.otus.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
