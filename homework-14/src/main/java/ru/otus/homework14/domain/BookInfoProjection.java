package ru.otus.homework14.domain;

import java.util.Set;

public interface BookInfoProjection {
    String getId();

    String getName();

    Set<Author> getAuthors();

    Set<Genre> getGenres();
}
