package ru.otus.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.domain.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Genre JPA repository test")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {
    public static final long FIRST_GENRE_ID = 1;
    @Autowired
    private GenreRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("should find entity by id")
    @Test
    void findById() {
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        Genre actualGenre = repositoryJpa.findById(FIRST_GENRE_ID).get();
        assertEquals(expectedGenre, actualGenre);
    }

    @DisplayName("should count all entities")
    @Test
    void count() {
        long expectedQuantity = em.getEntityManager().createQuery("select g from Genre g", Genre.class).
                getResultList().size();
        long actualQuantity = repositoryJpa.count();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Genre newGenre = new Genre();
        newGenre.setName("Brand new genre");
        repositoryJpa.save(newGenre);
        assertNotEquals(0, newGenre.getId());
    }

    @DisplayName("should persist entity")
    @Test
    void save() {
        Genre newGenre = new Genre();
        newGenre.setName("Brand new genre");
        repositoryJpa.save(newGenre);
        Genre foundGenre = em.find(Genre.class, newGenre.getId());
        assertEquals(newGenre, foundGenre);
    }

    @Test
    void findByName() {
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        Genre actualGenre = repositoryJpa.findByName(expectedGenre.getName()).get(0);
        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    void findAll() {
        Set<Genre> expectedGenres = new HashSet<>(em.getEntityManager().createQuery("select g from Genre g", Genre.class).
                getResultList());
        List<Genre> actualGenresList = repositoryJpa.findAll();
        Set<Genre> actualGenres = new HashSet<>(actualGenresList);
        assertEquals(actualGenres.size(), actualGenresList.size());
        assertEquals(expectedGenres, actualGenres);
    }

    @Test
    void delete() {
        repositoryJpa.delete(FIRST_GENRE_ID);
        assertNull(em.find(Genre.class, FIRST_GENRE_ID));
    }
}