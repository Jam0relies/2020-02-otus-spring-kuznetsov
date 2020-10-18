package ru.otus.homework13.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework13.domain.Book;
import ru.otus.homework13.domain.BookInfoProjection;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> findByName(String name);

    List<BookInfoProjection> findAllBookInfosBy();

    BookInfoProjection findBookInfoById(String id);

}
