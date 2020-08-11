package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByName(String name) {
        return em.createQuery("select b from Book b where b.name = :name", Book.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Book bookToDelete = em.find(Book.class, id);
        em.remove(bookToDelete);
    }
}
