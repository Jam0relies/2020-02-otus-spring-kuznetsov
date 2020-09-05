package ru.otus.homework11.repository;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ReplaceRootOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Comment;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


public class BookRepositoryImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    public BookRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<?> removeAuthorFromBook(String bookId, String authorId) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().pull("authors", new Query(Criteria.where("$id").is(new ObjectId(authorId)))),
                Book.class);
    }

    @Override
    public Mono<?> addAuthorToBook(String bookId, String authorId) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().addToSet("authors", new DBRef("authors", new ObjectId(authorId))),
                Book.class);
    }

    @Override
    public Mono<?> removeGenreFromBook(String bookId, String genre) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().pull("genres", new Query(Criteria.where("$id").is(new ObjectId(genre)))),
                Book.class);
    }

    @Override
    public Mono<?> addGenreToBook(String bookId, String genre) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().addToSet("genres", new DBRef("genres", new ObjectId(genre))),
                Book.class);
    }

    @Override
    public Flux<Comment> getAllComments(String bookId) {
        MatchOperation matchOperation = match(Criteria.where("_id").is(bookId));
        UnwindOperation unwindOperation = unwind("$comments");
        ReplaceRootOperation replaceRootOperation = replaceRoot("$comments");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, unwindOperation, replaceRootOperation);
        return mongoTemplate.aggregate(aggregation, "books", Comment.class);
    }

    @Override
    public Mono<?> addComment(String bookId, Comment comment) {
        return mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(bookId)),
                new Update().push("comments", comment), Book.class);
    }
}
