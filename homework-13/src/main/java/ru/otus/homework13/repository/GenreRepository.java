package ru.otus.homework13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework13.domain.Genre;

import java.util.List;
import java.util.Set;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByName(String name);

    List<Genre> findByIdNotIn(Set<String> ids);
}