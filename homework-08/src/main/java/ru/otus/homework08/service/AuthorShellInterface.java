package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.repository.AuthorRepository;


@ShellComponent
@RequiredArgsConstructor
public class AuthorShellInterface {
    private final AuthorRepository authorRepository;

    @ShellMethod(value = "Find author by id (String id)", key = {"authorById"})
    public String getById(@ShellOption String id) {
        return authorRepository.findById(id).toString();
    }

    @ShellMethod(value = "Find author by name (String name)", key = {"authorByName"})
    public String getByName(@ShellOption String name) {
        return authorRepository.findByName(name).toString();
    }

    @ShellMethod(value = "Add new author (String name)", key = {"addAuthor"})
    public String addAuthor(@ShellOption String name) {
        final Author author = new Author(name);
        final Author savedAuthor = authorRepository.save(author);
        return savedAuthor.toString();
    }

    @ShellMethod(value = "Get all authors", key = {"authors"})
    public String getAll() {
        return authorRepository.findAll().toString();
    }

    @ShellMethod(value = "Delete author by id (String id)", key = {"deleteAuthor"})
    public void delete(@ShellOption String id) {
        authorRepository.deleteById(id);
    }
}
