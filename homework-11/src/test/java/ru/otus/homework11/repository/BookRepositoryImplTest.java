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
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.mongock.MongockConfig;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void addAuthorToBook() {
        Book book = bookRepository.save(new Book("Add author test book")).block(Duration.ofSeconds(1));
        Author authorToAdd = authorRepository.findByName("Harold Abelson").blockFirst(Duration.ofSeconds(1));
        bookRepository.addAuthorToBook(book.getId(), authorToAdd.getId()).block(Duration.ofSeconds(1));
        StepVerifier.create(bookRepository.findById(book.getId()))
                .assertNext(foundBook -> assertTrue(foundBook.getAuthors().contains(authorToAdd), foundBook.toString()))
                .expectComplete().verify(Duration.ofSeconds(1));
    }

    @Test
    void addComment() {
        Comment newComment = new Comment("test comment", Instant.now());
        Book book = bookRepository.findByName("Hamlet").blockFirst(Duration.ofSeconds(1));
        bookRepository.addComment(book.getId(), newComment).block(Duration.ofSeconds(1));
        List<Comment> comments = bookRepository.getAllComments(book.getId()).buffer().blockLast(Duration.ofSeconds(1));
        assertEquals(newComment, comments.get(1));
        assertTrue(comments.contains(newComment), newComment.toString() + " " + comments.toString());
    }
}