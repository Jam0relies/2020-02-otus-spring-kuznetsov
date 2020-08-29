package ru.otus.homework10.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.domain.Genre;
import ru.otus.homework10.rest.dto.AddGenreRequestDto;
import ru.otus.homework10.rest.dto.GenreDto;
import ru.otus.homework10.service.GenreService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final ModelMapper modelMapper;

    public GenreController(GenreService genreService, ModelMapper modelMapper) {
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return genreService.getAll().stream().map(a -> modelMapper.map(a, GenreDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/genres/{genreId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable long genreId) {
        genreService.delete(genreId);
    }

    @PostMapping("/api/genres")
    public GenreDto addAuthor(@RequestBody @Valid AddGenreRequestDto genreToAdd) {
        Genre genre = genreService.addGenre(genreToAdd.getName());
        return modelMapper.map(genre, GenreDto.class);
    }
}
