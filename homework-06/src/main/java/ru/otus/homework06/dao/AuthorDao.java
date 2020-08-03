package ru.otus.homework06.dao;


import ru.otus.homework06.domain.Author;

import java.util.List;

public interface AuthorDao {
    long count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getByName(String name);

    List<Author> getAll();

    boolean delete(long id);
}
