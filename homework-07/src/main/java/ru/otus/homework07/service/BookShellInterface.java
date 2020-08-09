package ru.otus.homework07.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homework07.domain.Book;
import ru.otus.homework07.repository.BookRepository;

@ShellComponent
@RequiredArgsConstructor
public class BookShellInterface {
    private final BookRepository repository;

    @ShellMethod(value = "Find book by id (long id)", key = {"bookById"})
    public String getById(@ShellOption Long id) {
        return repository.findById(id).toString();
    }

    @ShellMethod(value = "Find author by name (String name)", key = {"bookByName"})
    public String getById(@ShellOption String name) {
        return repository.findByName(name).toString();
    }

    @ShellMethod(value = "Add new book (String name)", key = {"addBook"})
    public String addBook(@ShellOption String name) {
        final Book book = new Book(name);
        final Book savedBook = repository.save(book);
        return savedBook.toString();
    }

    @ShellMethod(value = "Get all books", key = {"books"})
    public String getAll() {
        return repository.findAll().toString();
    }

    @ShellMethod(value = "Delete book by id (long id)", key = {"deleteBook"})
    public String delete(@ShellOption long id) {
        return Boolean.toString(repository.delete(id));
    }

    @ShellMethod(value = "Add author to book by their id's (long bookId, long authorId)", key = {"addBookAuthor"})
    public String addAuthor(long bookId, long authorId) {
        return repository.addAuthorById(bookId, authorId).toString();
    }

    @ShellMethod(value = "Add genre to book by their id's (long bookId, long authorId)", key = {"addBookGenre"})
    public String addGenre(long bookId, long authorId) {
        return repository.addGenreById(bookId, authorId).toString();
    }
}
