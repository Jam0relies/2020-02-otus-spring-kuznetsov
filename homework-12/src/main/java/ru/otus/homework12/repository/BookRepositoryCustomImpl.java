package ru.otus.homework12.repository;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.homework12.domain.Book;
import ru.otus.homework12.domain.Comment;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void removeAuthorFromBook(String bookId, String authorId) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().pull("authors", new Query(Criteria.where("$id").is(new ObjectId(authorId)))),
                Book.class);
    }

    @Override
    public void addAuthorToBook(String bookId, String authorId) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().addToSet("authors", new DBRef("authors", new ObjectId(authorId))),
                Book.class);
    }

    @Override
    public void removeGenreFromBook(String bookId, String genre) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().pull("genres", new Query(Criteria.where("$id").is(new ObjectId(genre)))),
                Book.class);
    }

    @Override
    public void addGenreToBook(String bookId, String genre) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().addToSet("genres", new DBRef("genres", new ObjectId(genre))),
                Book.class);
    }

    @Override
    public List<Comment> getAllComments(String bookId) {
        MatchOperation matchOperation = match(Criteria.where("_id").is(bookId));
        UnwindOperation unwindOperation = unwind("$comments");
        ReplaceRootOperation replaceRootOperation = replaceRoot("$comments");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, unwindOperation, replaceRootOperation);
        return mongoTemplate.aggregate(aggregation, "books", Comment.class).getMappedResults();
    }

    @Override
    public void addComment(String bookId, Comment comment) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().push("comments", comment), Book.class);
    }
}
