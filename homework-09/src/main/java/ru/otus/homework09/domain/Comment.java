package ru.otus.homework09.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@ToString(exclude = {"book"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "text", nullable = false)
    private String text;

    @NonNull
    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @NonNull
    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Comment(String text, Instant timestamp, Book book) {
        this.text = text;
        this.timestamp = timestamp;
        this.book = book;
    }
}
