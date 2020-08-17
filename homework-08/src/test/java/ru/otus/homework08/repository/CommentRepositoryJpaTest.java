package ru.otus.homework08.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Comment;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Comment JPA repository test")
@DataMongoTest
class CommentRepositoryJpaTest {
    static final long FIRST_COMMENT_ID = 1;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
//    @Autowired
//    private TestEntityManager em;

//    @DisplayName("should count all entities")
//    @Test
//    void count() {
//        long expectedQuantity = em.getEntityManager().createQuery("select c from Comment c", Comment.class).
//                getResultList().size();
//        long actualQuantity = commentRepository.count();
//        assertEquals(expectedQuantity, actualQuantity);
//    }

    @DisplayName("should generate id on save")
    @Test
    void saveIdTest() {
        Comment newComment = new Comment("sometext", Instant.now(), new Book(1));
        commentRepository.save(newComment);
        assertNotNull(newComment.getId());
    }

//    @DisplayName("should persist entity")
//    @Test
//    void save() {
//        Comment newComment = new Comment("sometext", Instant.now(), new Book(1));
//        commentRepository.save(newComment);
//        Comment foundComment = em.find(Comment.class, newComment.getId());
//        em.refresh(foundComment);
//        assertEquals("Hamlet", foundComment.getBook().getName());
//        assertEquals(newComment, foundComment);
//    }

//    @DisplayName("should find entity by id")
//    @Test
//    void findById() {
//        Comment expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
//        Comment actualComment = commentRepository.findById(FIRST_COMMENT_ID).get();
//        assertEquals(expectedComment, actualComment);
//        assertEquals("Some comment", actualComment.getText());
//        assertEquals(1, actualComment.getBook().getId());
//    }

//    @DisplayName("should find all entities")
//    @Test
//    void findAll() {
//        Set<Comment> expectedComments = new HashSet<>(em.getEntityManager().createQuery("select c from Comment c", Comment.class).
//                getResultList());
//        List<Comment> actualCommentList = commentRepository.findAll();
//        Set<Comment> actualComments = new HashSet<>(actualCommentList);
//        assertEquals(actualComments.size(), actualCommentList.size());
//        assertEquals(expectedComments, actualComments);
//    }

//    @DisplayName("should delete entity")
//    @Test
//    void delete() {
//        commentRepository.deleteById(FIRST_COMMENT_ID);
//        assertNull(em.find(Comment.class, FIRST_COMMENT_ID));
//    }

//    @DisplayName("should find comment by book id")
//    @Test
//    void findByBookId() {
//        Set<Comment> expectedComments = new HashSet<>(em.find(Book.class, 1L).getComments());
//        List<Comment> actualCommentList = commentRepository.findByBookId(1);
//        Set<Comment> actualComments = new HashSet<>(actualCommentList);
//        assertEquals(actualComments.size(), actualCommentList.size());
//        assertEquals(expectedComments, actualComments);
//    }
}