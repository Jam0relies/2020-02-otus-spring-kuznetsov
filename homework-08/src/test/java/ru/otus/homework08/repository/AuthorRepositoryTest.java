package ru.otus.homework08.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework08.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Author JPA repository test")
@DataMongoTest
class AuthorRepositoryTest {
    public static final long FIRST_AUTHOR_ID = 1;
    @Autowired
    private AuthorRepository repositoryJpa;
    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("should find entity by name")
    @Test
    void findByName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Classic"));
        Author expectedGenre = mongoTemplate.find(query, Author.class).get(0);
        Author actualAuthor = repositoryJpa.findByName(expectedGenre.getName()).get(0);
        assertEquals(expectedGenre, actualAuthor);
    }

}