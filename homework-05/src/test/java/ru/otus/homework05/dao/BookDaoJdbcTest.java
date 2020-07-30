package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Book dao jdbc test")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void count() {
        bookDaoJdbc.count();
    }

    @Test
    void insert() {
    }

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(1);
        System.out.println(book);
        assertEquals("Hamlet", book.getName());
        assertEquals(1, book.getAuthors().size());
        assertEquals("William Shakespeare", book.getAuthors().stream().findFirst().get().getName());
        assertEquals(1, book.getGenres().size());
        assertEquals("Classic", book.getGenres().stream().findFirst().get().getName());
    }

    @Test
    void getByName() {
    }

    @Test
    void getAll() {
    }
}