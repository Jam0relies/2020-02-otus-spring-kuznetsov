package ru.otus.homework12.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework12.domain.Author;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByName(String name);

    List<Author> findByIdNotIn(Set<String> ids);
}
