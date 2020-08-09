package ru.otus.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework07.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    List<Book> findByName(String name);
}
