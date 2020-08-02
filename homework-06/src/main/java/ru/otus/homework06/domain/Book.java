package ru.otus.homework05.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
public class Book {
    private final long id;
    private final String name;
    @NonNull
    private final Set<Author> authors;
    @NonNull
    private final Set<Genre> genres;
}
