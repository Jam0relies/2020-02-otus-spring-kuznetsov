package ru.otus.homework05.dao;

import ru.otus.homework05.domain.Book;

import java.util.List;


public interface BookDao {
    long count();

    void insert(Book book);

    Book getById(long id);

    List<Book> getByName(String name);

    List<Book> getAll();

    boolean delete(long id);
}
