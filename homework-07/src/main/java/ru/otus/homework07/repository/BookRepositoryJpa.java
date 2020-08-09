package ru.otus.homework07.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework07.domain.Author;
import ru.otus.homework07.domain.Book;
import ru.otus.homework07.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class BookRepositoryJpa {
    @PersistenceContext
    private EntityManager em;

    //    @Override
    @Transactional
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    //    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    //    @Override
    @Transactional
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    //    @Override
    @Transactional
    public List<Book> findByName(String name) {
        return em.createQuery("select b from Book b where b.name = :name", Book.class)
                .setParameter("name", name).getResultList();
    }

    //    @Override
    @Transactional
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    //    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("delete from Book b where b.id = : id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }

    //    @Override
    @Transactional
    public Book addAuthorById(long bookId, long authorId) {
        Book book = this.findById(bookId).get();
        Author author = new Author(authorId, null);
        Set<Author> authors = book.getAuthors();
        authors.add(author);
        book.setAuthors(authors);
        return this.save(book);
    }

    //    @Override
    @Transactional
    public Book addGenreById(long bookId, long genreId) {
        Book book = this.findById(bookId).get();
        Genre genre = new Genre(genreId, null);
        Set<Genre> genres = book.getGenres();
        genres.add(genre);
        book.setGenres(genres);
        return this.save(book);
    }
}
