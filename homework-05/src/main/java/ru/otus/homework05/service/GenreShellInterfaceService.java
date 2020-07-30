package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.GenreDao;

@ShellComponent
@RequiredArgsConstructor
public class GenreShellInterfaceService {
    private final GenreDao genreDao;

    @ShellMethod(value = "Find genre by id (long)", key = {"genreById"})
    public String getById(@ShellOption Long id) {
        return genreDao.getById(id).toString();
    }

    @ShellMethod(value = "Find genre by name (String)", key = {"genreByName"})
    public String getById(@ShellOption String name) {
        return genreDao.getByName(name).toString();
    }
}
