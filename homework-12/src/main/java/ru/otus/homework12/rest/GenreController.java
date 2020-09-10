package ru.otus.homework12.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework12.domain.Genre;
import ru.otus.homework12.repository.GenreRepository;
import ru.otus.homework12.rest.dto.AddGenreRequestDto;
import ru.otus.homework12.rest.dto.GenreDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class GenreController {
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    public GenreController(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/genres")
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll().stream().map(a -> modelMapper.map(a, GenreDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/genres/{genreId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String genreId) {
        genreRepository.deleteById(genreId);
    }

    @PostMapping("/api/genres")
    public GenreDto addGenre(@RequestBody @Valid AddGenreRequestDto genreToAdd) {
        Genre genre = genreRepository.save(new Genre(genreToAdd.getName()));
        return modelMapper.map(genre, GenreDto.class);
    }
}
