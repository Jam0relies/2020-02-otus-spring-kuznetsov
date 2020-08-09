package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.domain.Genre;
import ru.otus.homework07.repository.GenreRepository;


@ShellComponent
@RequiredArgsConstructor
public class GenreShellInterface {
    private final GenreRepository repository;

    @ShellMethod(value = "Find genre by id (long id)", key = {"genreById"})
    public String getById(@ShellOption Long id) {
        return repository.findById(id).toString();
    }

    @ShellMethod(value = "Find genre by name (String name)", key = {"genreByName"})
    public String getById(@ShellOption String name) {
        return repository.findByName(name).toString();
    }

    @ShellMethod(value = "Add new genre (long id, String name)", key = {"addGenre"})
    public String addGenre(@ShellOption long id, @ShellOption String name) {
        final Genre genre = new Genre(id, name);
        final Genre savedGenre = repository.save(genre);
        return savedGenre.toString();
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public String getAll() {
        return repository.findAll().toString();
    }

    @ShellMethod(value = "Delete genre by id (long id)", key = {"deleteGenre"})
    public void delete(@ShellOption long id) {
        repository.deleteById(id);
    }
}
