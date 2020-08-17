package ru.otus.homework08.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework08.domain.Author;
import ru.otus.homework08.domain.Book;
import ru.otus.homework08.domain.Genre;
import ru.otus.homework08.repository.AuthorRepository;
import ru.otus.homework08.repository.BookRepository;
import ru.otus.homework08.repository.GenreRepository;

@ShellComponent
@RequiredArgsConstructor
public class BookShellInterface {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @ShellMethod(value = "Find book by id (String id)", key = {"bookById"})
    public String getById(@ShellOption String id) {
        return bookRepository.findById(id).get().toString();
    }

    @ShellMethod(value = "Find author by name (String name)", key = {"bookByName"})
    public String getByName(@ShellOption String name) {
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

    @ShellMethod(value = "Delete book by id (String id)", key = {"deleteBook"})
    public void delete(@ShellOption String id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    @ShellMethod(value = "Add author to book by their id's (long bookId, long authorId)", key = {"addBookAuthor"})
    public String addAuthor(String bookId, String authorId) {
        Book book = bookRepository.findById(bookId).get();
        Author author = authorRepository.findById(authorId).get();
        book.getAuthors().add(author);
        return book.toString();
    }

    @Transactional
    @ShellMethod(value = "Add genre to book by their id's (long bookId, long authorId)", key = {"addBookGenre"})
    public String addGenre(String bookId, String genreId) {
        Book book = bookRepository.findById(bookId).get();
        Genre genre = genreRepository.findById(genreId).get();
        book.getGenres().add(genre);
        return book.toString();
    }
}
