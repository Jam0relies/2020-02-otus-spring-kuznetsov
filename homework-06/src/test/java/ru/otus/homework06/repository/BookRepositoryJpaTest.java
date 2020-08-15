package ru.otus.homework06.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Comment;
import ru.otus.homework06.domain.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Book JPA repository test")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {
    public static final long FIRST_BOOK_ID = 1;
    public static final int EXPECTED_QUERIES_COUNT_PER_BOOK = 1;
    @Autowired
    private BookRepositoryJpa repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("should count all entities")
    @Test
    void count() {
        long expectedQuantity = em.getEntityManager().createQuery("select b from Book b", Book.class).
                getResultList().size();
        long actualQuantity = repositoryJpa.count();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Book newBook = new Book();
        newBook.setName("new book");
        repositoryJpa.save(newBook);
        assertNotEquals(0, newBook.getId());
    }

    @DisplayName("should persist entity")
    @Test
    void save() {
        Book newBook = new Book();
        newBook.setName("new book");
        repositoryJpa.save(newBook);
        Book foundAuthor = em.find(Book.class, newBook.getId());
        assertEquals(newBook, foundAuthor);
    }

    @DisplayName("should find entity by id")
    @Test
    void findById() {
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        Book actualBook = repositoryJpa.findById(FIRST_BOOK_ID).get();
        assertEquals(expectedBook, actualBook);
        assertEquals(1, actualBook.getAuthors().size());
        assertEquals("William Shakespeare", actualBook.getAuthors().toArray(new Author[1])[0].getName());
        assertEquals(1, actualBook.getGenres().size());
        assertEquals("Classic", actualBook.getGenres().toArray(new Genre[1])[0].getName());
    }

    @DisplayName("should get book with authors & genres by one request")
    @Test
    void countRequests() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        Book actualBook = repositoryJpa.findById(FIRST_BOOK_ID).get();
        assertEquals(1, actualBook.getAuthors().size());
        assertEquals(1, actualBook.getGenres().size());
        assertEquals(EXPECTED_QUERIES_COUNT_PER_BOOK, sessionFactory.getStatistics().getPrepareStatementCount());
    }

    @DisplayName("should find entity by name")
    @Test
    void findByName() {
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        Book actualBook = repositoryJpa.findByName(expectedBook.getName()).get(0);
        assertEquals(expectedBook, actualBook);
    }


    @DisplayName("should find all entities")
    @Test
    void findAll() {
        Set<Book> expectedBooks = new HashSet<>(em.getEntityManager().createQuery("select b from Book b", Book.class).
                getResultList());
        List<Book> actualBookList = repositoryJpa.findAll();
        Set<Book> actualBooks = new HashSet<>(actualBookList);
        assertEquals(actualBooks.size(), actualBookList.size());
        assertEquals(expectedBooks, actualBooks);
    }

    @DisplayName("should delete entity")
    @Test
    void delete() {
        repositoryJpa.delete(FIRST_BOOK_ID);
        assertNull(em.find(Book.class, FIRST_BOOK_ID));
    }

    @DisplayName("should delete book, but not comments")
    @Test
    void commentShouldBeDeleted() {
        repositoryJpa.delete(FIRST_BOOK_ID);
        assertNull(em.find(Comment.class, 1L));
    }

    @DisplayName("should delete book, but not author")
    @Test
    void authorShouldNotBeDeleted() {
        repositoryJpa.delete(FIRST_BOOK_ID);
        assertNotNull(em.find(Author.class, 1L));
    }

    @DisplayName("should delete book, but not genre")
    @Test
    void genreShouldNotBeDeleted() {
        repositoryJpa.delete(FIRST_BOOK_ID);
        assertNotNull(em.find(Genre.class, 1L));
    }
}