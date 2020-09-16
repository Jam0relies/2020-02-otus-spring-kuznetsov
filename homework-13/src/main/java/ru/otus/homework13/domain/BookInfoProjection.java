package ru.otus.homework13.domain;

import java.util.Set;

public interface BookInfoProjection {
    String getId();

    String getName();

    Set<Author> getAuthors();

    Set<Genre> getGenres();
}
