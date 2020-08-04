package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public long count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public List<Author> findByName(String name) {
        return em.createQuery("select a from Author a where a.name = :name", Author.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public boolean delete(long id) {
        return em.createQuery("delete from Author a where a.id = : id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }
}
