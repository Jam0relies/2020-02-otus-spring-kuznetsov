package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
