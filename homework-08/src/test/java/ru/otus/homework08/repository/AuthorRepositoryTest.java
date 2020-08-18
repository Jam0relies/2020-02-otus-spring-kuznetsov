package ru.otus.homework08.repository;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework08.domain.Author;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Author repository test")
@DataMongoTest
@EnableMongock
class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository repositoryJpa;
    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("should find entity by name")
    @Test
    void findByName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("William Shakespeare"));
        Author expectedAuthor = mongoTemplate.find(query, Author.class).get(0);
        Author actualAuthor = repositoryJpa.findByName(expectedAuthor.getName()).get(0);
        assertEquals(expectedAuthor, actualAuthor);
    }

}