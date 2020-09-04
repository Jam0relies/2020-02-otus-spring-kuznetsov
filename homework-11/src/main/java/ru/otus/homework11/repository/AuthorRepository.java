package ru.otus.homework11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.homework11.domain.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Flux<Author> findByName(String name);

    Flux<Author> findByIdNotIn(Flux<String> ids);
}
