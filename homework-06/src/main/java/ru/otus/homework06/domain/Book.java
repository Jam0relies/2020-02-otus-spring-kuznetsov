package ru.otus.homework06.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY /*, cascade = CascadeType.ALL*/)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @NonNull
    private Set<Author> authors;
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY /*, cascade = CascadeType.ALL*/)
    @JoinTable(name = "book_genre", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @NonNull
    private Set<Genre> genres;
}
