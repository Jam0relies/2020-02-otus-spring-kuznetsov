package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Book;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Book dao jdbc test")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    void count() {
        long oldQuantity = bookDaoJdbc.count();
        bookDaoJdbc.insert(new Book(123456, "Some book", Collections.emptySet(), Collections.emptySet()));
        long newQuantity = bookDaoJdbc.count();
        assertEquals(oldQuantity + 1, newQuantity);
    }

    @Test
    void insert() {
        Book book = new Book(123456, "New book", Collections.emptySet(), Collections.emptySet());
        bookDaoJdbc.insert(book);
        Book found = bookDaoJdbc.getById(123456);
        assertEquals("New book", found.getName());
    }

    @Test
    void getById() {
        Book book = bookDaoJdbc.getById(1);
        System.out.println(book);
        assertEquals(1, book.getId());
        assertEquals("Hamlet", book.getName());
        assertEquals(1, book.getAuthors().size());
        assertEquals("William Shakespeare", book.getAuthors().stream().findFirst().get().getName());
        assertEquals(1, book.getGenres().size());
        assertEquals("Classic", book.getGenres().stream().findFirst().get().getName());
    }

    @Test
    void getByName() {
        List<Book> books = bookDaoJdbc.getByName("Hamlet");
        assertEquals(1, books.size());
        Book book = books.get(0);
        assertEquals(1, book.getId());
        assertEquals("Hamlet", book.getName());
        assertEquals(1, book.getAuthors().size());
        assertEquals("William Shakespeare", book.getAuthors().stream().findFirst().get().getName());
        assertEquals(1, book.getGenres().size());
        assertEquals("Classic", book.getGenres().stream().findFirst().get().getName());
    }

    @Test
    void getAll() {
        List<Book> found = bookDaoJdbc.getAll();
        assertEquals(bookDaoJdbc.count(), found.size());
    }

    @Test
    void delete() {
        bookDaoJdbc.delete(1);
        assertNull(bookDaoJdbc.getById(1));
    }
}