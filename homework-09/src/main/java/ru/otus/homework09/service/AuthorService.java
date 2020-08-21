package ru.otus.homework09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homework09.domain.Author;
import ru.otus.homework09.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    //    @ShellMethod(value = "Find author by id (long id)", key = {"authorById"})
    public String getById(Long id) {
        return authorRepository.findById(id).toString();
    }

    //    @ShellMethod(value = "Find author by name (String name)", key = {"authorByName"})
    public String getByName(String name) {
        return authorRepository.findByName(name).toString();
    }

    //    @ShellMethod(value = "Add new author (long id, String name)", key = {"addAuthor"})
    public String addAuthor(long id, String name) {
        final Author author = new Author(id, name);
        final Author savedAuthor = authorRepository.save(author);
        return savedAuthor.toString();
    }

    //    @ShellMethod(value = "Get all authors", key = {"authors"})
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    //    @ShellMethod(value = "Delete author by id (long id)", key = {"deleteAuthor"})
    public void delete(long id) {
        authorRepository.deleteById(id);
    }
}
