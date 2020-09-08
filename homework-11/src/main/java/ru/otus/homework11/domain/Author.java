package ru.otus.homework11.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    @NonNull
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
