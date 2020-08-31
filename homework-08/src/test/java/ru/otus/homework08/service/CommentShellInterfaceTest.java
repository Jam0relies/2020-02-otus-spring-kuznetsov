package ru.otus.homework08.service;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.shell.Shell;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Comment;
import ru.otus.homework08.repository.BookRepository;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CommentShellInterfaceTest {
    @Autowired
    private Shell shell;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void getByBookId() {
        Book book = bookRepository.findByName("Hamlet").get(0);
        UUID commentId = ((UUID) book.getComments().entrySet().toArray(new Map.Entry[0])[0].getKey());

        Object res = shell.evaluate(() -> "commentsByBookId" + " " + book.getId() + " " + commentId);
        assertTrue(res instanceof String, res.toString());
    }

    @Test
    void addComment() {
        Book book = bookRepository.findByName("Structure and Interpretation of Computer Programs").get(0);
        String text = "text";
        Object res = shell.evaluate(() -> "addComment" + " " + book.getId() + " " + text);
        assertTrue(res instanceof String, res.toString());
        Book updatedBook = bookRepository.findByName("Structure and Interpretation of Computer Programs").get(0);
        assertEquals(text, updatedBook.getComments().values().toArray(new Comment[0])[0].getText());
    }

}