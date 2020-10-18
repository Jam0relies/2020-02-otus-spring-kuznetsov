package ru.otus.homework14.domain;

import lombok.Data;

@Data
public class BookCommentProjection {
    private String id;
    private Comment comments;
}
