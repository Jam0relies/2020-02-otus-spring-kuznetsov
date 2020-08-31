package ru.otus.homework10.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    private String text;
    private Instant timestamp;
}
