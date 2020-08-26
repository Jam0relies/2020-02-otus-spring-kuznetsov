package ru.otus.homework08.repository;


import com.github.cloudyrock.spring.v5.EnableMongock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework08.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Genre MongoDB repository test")
@DataMongoTest
@EnableMongock
class GenreRepositoryJpaTest {
    public static final String FIRST_GENRE_ID = "classic-id";
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    @DisplayName("should find entity by name")
    @Test
    void findByName() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Classic"));
        Genre expectedGenre = mongoTemplate.find(query, Genre.class).get(0);
        Genre actualGenre = genreRepository.findByName(expectedGenre.getName()).get(0);
        assertEquals(expectedGenre, actualGenre);
    }
}