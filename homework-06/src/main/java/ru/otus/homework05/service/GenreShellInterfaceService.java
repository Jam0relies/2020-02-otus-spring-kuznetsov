package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.GenreDao;
import ru.otus.homework05.domain.Genre;

@ShellComponent
@RequiredArgsConstructor
public class GenreShellInterfaceService {
    private final GenreDao genreDao;

    @ShellMethod(value = "Find genre by id (long id)", key = {"genreById"})
    public String getById(@ShellOption Long id) {
        return genreDao.getById(id).toString();
    }

    @ShellMethod(value = "Find genre by name (String name)", key = {"genreByName"})
    public String getById(@ShellOption String name) {
        return genreDao.getByName(name).toString();
    }

    @ShellMethod(value = "Add new genre (long id, String name)", key = {"addGenre"})
    public String addGenre(@ShellOption long id, @ShellOption String name) {
        Genre genre = new Genre(id, name);
        genreDao.insert(genre);
        return genre.toString();
    }

    @ShellMethod(value = "Get all genres", key = {"genres"})
    public String getAll() {
        return genreDao.getAll().toString();
    }

    @ShellMethod(value = "Delete genre by id (long id)", key = {"deleteGenre"})
    public String delete(@ShellOption long id) {
        return Boolean.toString(genreDao.delete(id));
    }
}
