package ru.otus.homework11.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.mongock.MongockConfig;

import java.time.Duration;
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
        Book book = bookRepository.findByName("Hamlet").blockFirst(Duration.ofSeconds(1));
        List<Comment> comments = bookRepository.getAllComments(book.getId()).buffer().blockLast(Duration.ofSeconds(1));
        assertNotNull(comments.get(0).getUuid());
    }
}