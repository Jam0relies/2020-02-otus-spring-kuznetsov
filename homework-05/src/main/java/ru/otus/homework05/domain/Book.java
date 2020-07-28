package ru.otus.homework05.domain;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private final long id;
    private final String name;
    private final List<Author> authors;
    private final List<Genre> genres;
}
