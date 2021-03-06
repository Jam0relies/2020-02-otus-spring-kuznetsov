package ru.otus.homework06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework06.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public long count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public List<Genre> findByName(String name) {
        return em.createQuery("select g from Genre g where g.name = :name", Genre.class)
                .setParameter("name", name).getResultList();
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Genre genreToDelete = em.find(Genre.class, id);
        em.remove(genreToDelete);
    }
}
