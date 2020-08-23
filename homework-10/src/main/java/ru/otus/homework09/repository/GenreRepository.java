package ru.otus.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework09.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);
}