package ru.otus.homework05.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
public class Genre {
    private final long id;
    private final String name;
}
