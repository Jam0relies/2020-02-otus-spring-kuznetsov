package ru.otus.homework11.repository;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;
import ru.otus.homework11.domain.Book;

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
                new Update().push("genres", new DBRef("genres", new ObjectId(genre))),
                Book.class);
    }
}
