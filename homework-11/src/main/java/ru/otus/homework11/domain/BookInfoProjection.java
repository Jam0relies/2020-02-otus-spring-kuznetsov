package ru.otus.homework11.domain;

import java.util.Set;

public interface BookInfoProjection {
    String getId();

    String getName();

    Set<Author> getAuthors();

    Set<Genre> getGenres();
}
