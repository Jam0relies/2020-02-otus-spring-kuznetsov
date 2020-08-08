package ru.otus.homework06.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Comment;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comment JPA repository test")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
class CommentRepositoryJpaTest {
    static final long FIRST_COMMENT_ID = 1;
    @Autowired
    private CommentRepository repositoryJpa;
    @Autowired
    private TestEntityManager em;

    @DisplayName("should count all entities")
    @Test
    void count() {
        long expectedQuantity = em.getEntityManager().createQuery("select c from Comment c", Comment.class).
                getResultList().size();
        long actualQuantity = repositoryJpa.count();
        assertEquals(expectedQuantity, actualQuantity);
    }

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Comment newComment = new Comment(0, "sometext", Instant.now(), new Book(1));
        repositoryJpa.save(newComment);
        assertNotEquals(0, newComment.getId());
    }

    @DisplayName("should persist entity")
    @Test
    void save() {
        Comment newComment = new Comment(0, "sometext", Instant.now(), new Book(1));
        repositoryJpa.save(newComment);
        Comment foundComment = em.find(Comment.class, newComment.getId());
        assertEquals(newComment, foundComment);
    }

    @DisplayName("should find entity by id")
    @Test
    void findById() {
        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        Comment actualComment = repositoryJpa.findById(FIRST_COMMENT_ID).get();
        assertEquals(expectedComment, actualComment);
        assertEquals("Some comment", actualComment.getText());
        assertEquals(1, actualComment.getBook().getId());
    }

    @Test
    void findAll() {
        Set<Comment> expectedComments = new HashSet<>(em.getEntityManager().createQuery("select c from Comment c", Comment.class).
                getResultList());
        List<Comment> actualCommentList = repositoryJpa.findAll();
        Set<Comment> actualComments = new HashSet<>(actualCommentList);
        assertEquals(actualComments.size(), actualCommentList.size());
        assertEquals(expectedComments, actualComments);
    }

    @Test
    void delete() {
        repositoryJpa.delete(FIRST_COMMENT_ID);
        assertNull(em.find(Comment.class, FIRST_COMMENT_ID));
    }

    @Test
    void findByBookId() {
        Set<Comment> expectedComments = new HashSet<>(em.find(Book.class, 1L).getComments());
        List<Comment> actualCommentList = repositoryJpa.findByBookId(1);
        Set<Comment> actualComments = new HashSet<>(actualCommentList);
        assertEquals(actualComments.size(), actualCommentList.size());
        assertEquals(expectedComments, actualComments);
    }
}