package ru.otus.homework12.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String name;

    @NonNull
    @DBRef
    private Set<Author> authors = new HashSet<>();

    @NonNull
    @DBRef
    private Set<Genre> genres = new HashSet<>();

    @NonNull
    private List<Comment> comments = new ArrayList<>();

    public Book(String name) {
        this.name = name;
    }
}
