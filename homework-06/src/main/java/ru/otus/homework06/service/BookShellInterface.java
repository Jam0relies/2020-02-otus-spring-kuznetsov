package ru.otus.homework06.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework06.domain.Author;
import ru.otus.homework06.domain.Book;
import ru.otus.homework06.domain.Genre;
import ru.otus.homework06.repository.AuthorRepository;
import ru.otus.homework06.repository.BookRepository;
import ru.otus.homework06.repository.GenreRepository;

import java.util.Set;

@ShellComponent
@RequiredArgsConstructor
public class BookShellInterface {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @ShellMethod(value = "Find book by id (long id)", key = {"bookById"})
    public String getById(@ShellOption Long id) {
        return bookRepository.findById(id).toString();
    }

    @ShellMethod(value = "Find author by name (String name)", key = {"bookByName"})
    public String getById(@ShellOption String name) {
        return bookRepository.findByName(name).toString();
    }

    @ShellMethod(value = "Add new book (String name)", key = {"addBook"})
    public String addBook(@ShellOption String name) {
        final Book book = new Book(name);
        final Book savedBook = bookRepository.save(book);
        return savedBook.toString();
    }

    @ShellMethod(value = "Get all books", key = {"books"})
    public String getAll() {
        return bookRepository.findAll().toString();
    }

    @ShellMethod(value = "Delete book by id (long id)", key = {"deleteBook"})
    public void delete(@ShellOption long id) {
        bookRepository.delete(id);
    }

    @ShellMethod(value = "Add author to book by their id's (long bookId, long authorId)", key = {"addBookAuthor"})
    @Transactional
    public String addAuthor(long bookId, long authorId) {
        Author authorToAdd = authorRepository.findById(authorId).get();
        Book book = bookRepository.findById(bookId).get();
        Set<Author> authors = book.getAuthors();
        authors.add(authorToAdd);
        return book.toString();
    }

    @ShellMethod(value = "Add genre to book by their id's (long bookId, long authorId)", key = {"addBookGenre"})
    @Transactional
    public String addGenre(long bookId, long genreId) {
        Genre genreToAdd = genreRepository.findById(genreId).get();
        Book book = bookRepository.findById(bookId).get();
        Set<Genre> genres = book.getGenres();
        genres.add(genreToAdd);
        return book.toString();
    }
}
