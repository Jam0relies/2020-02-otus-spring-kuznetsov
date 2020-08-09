package ru.otus.homework07.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework07.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public long count() {
        return em.createQuery("select count(c) from Comment c", Long.class).getSingleResult();
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    @Transactional
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    @Transactional
    public List<Comment> findByBookId(long bookId) {
        return em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return em.createQuery("delete from Comment c where c.id = : id")
                .setParameter("id", id)
                .executeUpdate() > 0;
    }
}
