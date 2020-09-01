package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework11.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByName(String name);
}
