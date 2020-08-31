package ru.otus.homework10.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;
}
