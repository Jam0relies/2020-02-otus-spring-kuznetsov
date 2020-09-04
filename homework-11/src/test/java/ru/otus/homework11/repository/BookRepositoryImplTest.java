package ru.otus.homework11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.mongock.MongockConfig;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
        String bookName = "Hamlet";
        Book book = bookRepository.findByName(bookName).blockFirst(Duration.ofSeconds(1));
        Author authorToRemove = authorRepository.findByName("William Shakespeare").blockFirst(Duration.ofSeconds(1));
        bookRepository.removeAuthorFromBook(book.getId(), authorToRemove.getId()).block(Duration.ofSeconds(1));
        assertFalse(bookRepository.findByName(bookName).blockFirst(Duration.ofSeconds(1)).getAuthors().contains(authorToRemove));
    }

    @Test
    void addAuthorToBook() throws Exception {
//
//        Book book = bookRepository.findByName("Hamlet").blockFirst();
//        Author authorToAdd = authorRepository.findByName("Harold Abelson").blockFirst(Duration.ofSeconds(1));
//        StepVerifier.create( bookRepository.addAuthorToBook(book.getId(), book.getId()))
//                .assertNext(result-> {
//
//                }).expectComplete().verify();
//        StepVerifier.create(bookRepository.findByName("Hamlet"))
//                .assertNext(book1 -> assertTrue(book.getAuthors().contains(authorToAdd)))
//                .expectComplete().verify();
//        bookRepository.addAuthorToBook(book.getId(), book.getId()).subscribe();//.block(Duration.ofSeconds(1));
//        System.out.println(bookRepository.findByName("Hamlet").blockFirst(Duration.ofSeconds(1)));
//        assertTrue(bookRepository.findByName("Hamlet").blockFirst(Duration.ofSeconds(1)).getAuthors().contains(authorToAdd));
    }
}