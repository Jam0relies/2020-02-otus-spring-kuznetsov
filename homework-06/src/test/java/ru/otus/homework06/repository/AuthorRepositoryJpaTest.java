package ru.otus.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.domain.Author;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Author JPA repository test")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {
    public static final long FIRST_AUTHOR_ID = 1;
    @Autowired
    private AuthorRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("should find entity by id")
    @Test
    void findById() {
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        Author actualAuthor = repositoryJpa.findById(FIRST_AUTHOR_ID).get();
        assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("should count all entities")
    @Test
    void count() {
        long expectedQuantity = em.getEntityManager().createQuery("select a from Author a", Author.class).
                getResultList().size();
        long actualQuantity = repositoryJpa.count();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Author newAuthor = new Author();
        newAuthor.setName("new author");
        repositoryJpa.save(newAuthor);
        assertNotEquals(0, newAuthor.getId());
    }

    @DisplayName("should persist entity")
    @Test
    void save() {
        Author newAuthor = new Author();
        newAuthor.setName("new author");
        repositoryJpa.save(newAuthor);
        Author foundAuthor = em.find(Author.class, newAuthor.getId());
        assertEquals(newAuthor, foundAuthor);
    }

    @DisplayName("should find entity by name")
    @Test
    void findByName() {
        Author expectedGenre = em.find(Author.class, FIRST_AUTHOR_ID);
        Author actualAuthor = repositoryJpa.findByName(expectedGenre.getName()).get(0);
        assertEquals(expectedGenre, actualAuthor);
    }

    @DisplayName("should find all entities")
    @Test
    void findAll() {
        Set<Author> expectedAuthors = new HashSet<>(em.getEntityManager().createQuery("select a from Author a", Author.class).
                getResultList());
        List<Author> actualAuthorsList = repositoryJpa.findAll();
        Set<Author> actualAuthors = new HashSet<>(actualAuthorsList);
        assertEquals(actualAuthors.size(), actualAuthorsList.size());
        assertEquals(expectedAuthors, actualAuthors);
    }

    @DisplayName("should delete entity")
    @Test
    void delete() {
        repositoryJpa.delete(FIRST_AUTHOR_ID);
        assertNull(em.find(Author.class, FIRST_AUTHOR_ID));
    }
}