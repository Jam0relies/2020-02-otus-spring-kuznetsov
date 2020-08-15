package ru.otus.homework08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework08.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByName(String name);
}
