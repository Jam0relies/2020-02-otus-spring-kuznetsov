package ru.otus.homework13.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "uuid")
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    private UUID uuid;
    @NonNull
    private String text;

    @NonNull
    private Instant timestamp;

    public Comment(String text, Instant timestamp) {
        this.uuid = UUID.randomUUID();
        this.text = text;
        this.timestamp = timestamp;
    }
}
