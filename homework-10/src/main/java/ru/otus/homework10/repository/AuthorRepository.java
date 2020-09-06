package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
      List<Author> findByName(String name);
}
