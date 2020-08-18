package ru.otus.homework08.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework08.domain.Book;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Book JPA repository test")
@DataMongoTest
class BookRepositoryJpaTest {
    @Autowired
    private BookRepository repository;

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Book newBook = new Book();
        newBook.setName("new book");
        repository.save(newBook);
        assertNotNull(newBook.getId());
    }
}