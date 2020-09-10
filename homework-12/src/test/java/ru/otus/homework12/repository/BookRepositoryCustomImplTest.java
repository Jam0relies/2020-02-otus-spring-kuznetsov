package ru.otus.homework12.repository;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework12.domain.Author;
import ru.otus.homework12.domain.Book;
import ru.otus.homework12.domain.Comment;
import ru.otus.homework12.mongock.MongockConfig;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Custom book repository test")
@Slf4j
@DataMongoTest
@Import(MongockConfig.class)
class BookRepositoryCustomImplTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void removeAuthorFromBook() {
        Book book = bookRepository.save(new Book("Remove author test book"));
        Author author = authorRepository.findByName("Harold Abelson").get(0);
        bookRepository.addAuthorToBook(book.getId(), author.getId());
        bookRepository.removeAuthorFromBook(book.getId(), author.getId());
        Book actualBook = bookRepository.findById(book.getId()).get();
        Assertions.assertThat(actualBook.getAuthors())
                .doesNotContain(author);
    }

    @Test
    void addAuthorToBook() {
        Book book = bookRepository.save(new Book("Add author test book"));
        Author authorToAdd = authorRepository.findByName("Harold Abelson").get(0);
        bookRepository.addAuthorToBook(book.getId(), authorToAdd.getId());
        Book actualBook = bookRepository.findById(book.getId()).get();
        Assertions.assertThat(actualBook.getAuthors()).contains(authorToAdd);
    }

    @Test
    void addComment() {
        Comment newComment = new Comment("test comment", Instant.now());
        Book book = bookRepository.findByName("Hamlet").get(0);
        bookRepository.addComment(book.getId(), newComment);
        List<Comment> comments = bookRepository.getAllComments(book.getId());
        assertEquals(newComment, comments.get(1));
        assertTrue(comments.contains(newComment), newComment.toString() + " " + comments.toString());
    }
}