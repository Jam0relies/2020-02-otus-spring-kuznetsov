package ru.otus.homework05.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework05.dao.AuthorDao;
import ru.otus.homework05.domain.Author;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellInterface {
    private final AuthorDao authorDao;

    @ShellMethod(value = "Find author by id (long id)", key = {"authorById"})
    public String getById(@ShellOption Long id) {
        return authorDao.getById(id).toString();
    }

    @ShellMethod(value = "Find author by name (String name)", key = {"authorByName"})
    public String getById(@ShellOption String name) {
        return authorDao.getByName(name).toString();
    }

    @ShellMethod(value = "Add new author (long id, String name)", key = {"addAuthor"})
    public String addAuthor(@ShellOption long id, @ShellOption String name) {
        Author author = new Author(id, name);
        authorDao.insert(author);
        return author.toString();
    }

    @ShellMethod(value = "Get all authors", key = {"authors"})
    public String getAll() {
        return authorDao.getAll().toString();
    }

    @ShellMethod(value = "Delete author by id (long id)", key = {"deleteAuthor"})
    public String delete(@ShellOption long id) {
        return Boolean.toString(authorDao.delete(id));
    }
}
