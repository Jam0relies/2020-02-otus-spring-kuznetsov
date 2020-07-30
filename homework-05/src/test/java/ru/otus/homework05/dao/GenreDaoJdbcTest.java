package ru.otus.homework05.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.homework05.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Genre dao jdbc test")
@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void count() {
        genreDaoJdbc.count();
    }

    @Test
    void getAll() {
    }

    @DisplayName(" should find proper genre")
    @Test
    void getById() {
        Genre genre = genreDaoJdbc.getById(1);
        assertEquals(genre.getName(), "Classic");
    }

    @DisplayName(" should find genre by name")
    @Test
    void getByName() {
        List<Genre> genres = genreDaoJdbc.getByName("Classic");
        assertEquals(1, genres.size());
        assertEquals(1, genres.get(0).getId());
    }

    @Test
    void insert() {
        Genre genre = new Genre(123456, "New book");
        genreDaoJdbc.insert(genre);
        Genre found = genreDaoJdbc.getById(123456);
        assertEquals("New book", found.getName());
    }

}