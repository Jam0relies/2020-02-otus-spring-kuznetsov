package ru.otus.homework12.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework12.domain.Book;
import ru.otus.homework12.domain.BookInfoProjection;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    List<Book> findByName(String name);

    List<BookInfoProjection> findAllBookInfosBy();

    BookInfoProjection findBookInfoById(String id);

}
