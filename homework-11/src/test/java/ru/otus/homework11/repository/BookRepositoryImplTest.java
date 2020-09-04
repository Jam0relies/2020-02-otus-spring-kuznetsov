package ru.otus.homework11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.mongock.MongockConfig;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Custom book repository test")
@Slf4j
@DataMongoTest
@Import(MongockConfig.class)
class BookRepositoryImplTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void removeAuthorFromBook() {
        Book book = bookRepository.save(new Book("Remove author test book")).block(Duration.ofSeconds(1));
        Author author = authorRepository.findByName("Harold Abelson").blockFirst(Duration.ofSeconds(1));
        bookRepository.addAuthorToBook(book.getId(), author.getId()).block(Duration.ofSeconds(1));
        StepVerifier.create(bookRepository.removeAuthorFromBook(book.getId(), author.getId())
                .then(bookRepository.findById(book.getId())))
                .assertNext(foundBook -> {
                    assertFalse(book.getAuthors().contains(author));
                }).expectComplete().verify(Duration.ofSeconds(1));
    }

    @Test
    void addAuthorToBook() throws Exception {
        Book book = bookRepository.save(new Book("Add author test book")).block(Duration.ofSeconds(1));
        Author authorToAdd = authorRepository.findByName("Harold Abelson").blockFirst(Duration.ofSeconds(1));
        Object result = bookRepository.addAuthorToBook(book.getId(), authorToAdd.getId()).block(Duration.ofSeconds(1));
        StepVerifier.create(bookRepository.findById(book.getId()))
                .assertNext(foundBook -> assertTrue(foundBook.getAuthors().contains(authorToAdd), foundBook.toString()))
                .expectComplete().verify(Duration.ofSeconds(1));
    }
}