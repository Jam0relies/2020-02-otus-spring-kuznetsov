package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    void insert(Genre genre);

    Genre getById(long id);

    List<Genre> getByName(String name);

    List<Genre> getAll();
}
