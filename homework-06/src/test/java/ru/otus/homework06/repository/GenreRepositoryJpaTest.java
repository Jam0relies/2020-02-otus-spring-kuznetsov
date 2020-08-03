package ru.otus.homework06.repository;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.domain.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    public static final long FIRST_GENRE_ID = 1;
    @Autowired
    private GenreRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @Test
    void findById() {
        Genre actualGenre = repositoryJpa.findById(FIRST_GENRE_ID).get();
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertEquals(expectedGenre, actualGenre);
    }
}