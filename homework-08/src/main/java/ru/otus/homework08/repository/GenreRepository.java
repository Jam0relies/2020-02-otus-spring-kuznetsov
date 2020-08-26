package ru.otus.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework08.domain.Genre;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);
}