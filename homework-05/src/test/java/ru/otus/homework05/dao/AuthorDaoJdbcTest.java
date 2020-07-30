package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Author dao jdbc test")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void count() {
        authorDaoJdbc.count();
    }

    @Test
    void insert() {
        Author author = new Author(123456, "New Author");
        authorDaoJdbc.insert(author);
        Author found = authorDaoJdbc.getById(123456);
        assertEquals("New Author", found.getName());
    }

    @Test
    void getById() {
        Author author = authorDaoJdbc.getById(1);
        assertEquals("William Shakespeare", author.getName());
    }

    @Test
    void getByName() {
        List<Author> author = authorDaoJdbc.getByName("William Shakespeare");
        assertEquals(1, author.size());
        assertEquals(1, author.get(0).getId());
    }

    @Test
    void getAll() {
        authorDaoJdbc.getAll();
    }
}