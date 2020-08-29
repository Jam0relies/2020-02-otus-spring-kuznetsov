package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
