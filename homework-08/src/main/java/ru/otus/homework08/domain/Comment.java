package ru.otus.homework08.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@ToString(exclude = {"book"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @NonNull
    private String text;

    @NonNull
    private Instant timestamp;

    @NonNull
    private Book book;

    public Comment(String text, Instant timestamp, Book book) {
        this.text = text;
        this.timestamp = timestamp;
        this.book = book;
    }
}
