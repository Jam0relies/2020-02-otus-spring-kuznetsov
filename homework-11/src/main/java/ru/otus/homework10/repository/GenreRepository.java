package ru.otus.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework10.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByName(String name);
}