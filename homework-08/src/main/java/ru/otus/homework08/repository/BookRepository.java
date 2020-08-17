package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByName(String name);
}
