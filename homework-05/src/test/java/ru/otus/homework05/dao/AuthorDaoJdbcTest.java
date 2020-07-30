package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Author;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Author dao jdbc test")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void count() {
        long oldQuantity = authorDao.count();
        authorDao.insert(new Author(123456, "Some author"));
        long newQuantity = authorDao.count();
        assertEquals(oldQuantity + 1, newQuantity);
    }

    @Test
    void insert() {
        Author author = new Author(123456, "New Author");
        authorDao.insert(author);
        Author found = authorDao.getById(123456);
        assertEquals("New Author", found.getName());
    }

    @Test
    void getById() {
        Author author = authorDao.getById(1);
        assertEquals("William Shakespeare", author.getName());
    }

    @Test
    void getByName() {
        List<Author> author = authorDao.getByName("William Shakespeare");
        assertEquals(1, author.size());
        assertEquals(1, author.get(0).getId());
    }

    @Test
    void getAll() {
        List<Author> found = authorDao.getAll();
        assertEquals(authorDao.count(), found.size());
    }

    @Test
    void delete() {
        authorDao.delete(1);
        assertThrows(EmptyResultDataAccessException.class, () -> authorDao.getById(1));
    }
}