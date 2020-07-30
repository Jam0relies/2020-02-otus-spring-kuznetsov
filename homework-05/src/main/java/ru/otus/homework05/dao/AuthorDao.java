package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Author;

import java.util.List;

public interface AuthorDao {
    long count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();
}
