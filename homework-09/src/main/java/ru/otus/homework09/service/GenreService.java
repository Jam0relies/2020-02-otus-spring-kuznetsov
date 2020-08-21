package ru.otus.homework09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homework09.domain.Genre;
import ru.otus.homework09.repository.GenreRepository;

import java.util.List;


@Component
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository repository;

    //    @ShellMethod(value = "Find genre by id (long id)", key = {"genreById"})
    public String getById(Long id) {
        return repository.findById(id).toString();
    }

    //    @ShellMethod(value = "Find genre by name (String name)", key = {"genreByName"})
    public String getById(String name) {
        return repository.findByName(name).toString();
    }

    //    @ShellMethod(value = "Add new genre (long id, String name)", key = {"addGenre"})
    public String addGenre(long id, String name) {
        final Genre genre = new Genre(id, name);
        final Genre savedGenre = repository.save(genre);
        return savedGenre.toString();
    }

    //    @ShellMethod(value = "Get all genres", key = {"genres"})
    public List<Genre> getAll() {
        return repository.findAll();
    }

    //    @ShellMethod(value = "Delete genre by id (long id)", key = {"deleteGenre"})
    public void delete(long id) {
        repository.deleteById(id);
    }
}
