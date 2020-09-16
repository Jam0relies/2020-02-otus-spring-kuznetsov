package ru.otus.homework13.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework13.domain.Book;
import ru.otus.homework13.domain.Comment;
import ru.otus.homework13.mongock.MongockConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("book repository test")
@Slf4j
@DataMongoTest
@Import(MongockConfig.class)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void commentId() {
        Book book = bookRepository.findByName("Hamlet").get(0);
        List<Comment> comments = bookRepository.getAllComments(book.getId());
        assertNotNull(comments.get(0).getUuid());
    }
}