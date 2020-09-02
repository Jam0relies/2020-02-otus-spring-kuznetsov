package ru.otus.homework11.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.BookInfoProjection;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findByName(String name);

    @Query("{}")
    Flux<BookInfoProjection> findAllBookInfos();

    @Query("{id: ?#{[0]} }")
    Mono<BookInfoProjection> findBookInfoById(String id);
}
