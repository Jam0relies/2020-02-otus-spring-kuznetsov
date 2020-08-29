package ru.otus.homework10.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private long id;
    private String name;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;
}
