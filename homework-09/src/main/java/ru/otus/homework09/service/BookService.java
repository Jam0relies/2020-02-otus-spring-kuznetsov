package ru.otus.homework09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework09.domain.Author;
import ru.otus.homework09.domain.Book;
import ru.otus.homework09.domain.Genre;
import ru.otus.homework09.repository.AuthorRepository;
import ru.otus.homework09.repository.BookRepository;
import ru.otus.homework09.repository.GenreRepository;

import java.util.List;

//@ShellComponent
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    //    @ShellMethod(value = "Find book by id (long id)", key = {"bookById"})
    public Book getById(long id) {
        return bookRepository.getOne(id);
    }

    //    @ShellMethod(value = "Find author by name (String name)", key = {"bookByName"})
    public String getById(String name) {
        return bookRepository.findByName(name).toString();
    }

    //    @ShellMethod(value = "Add new book (String name)", key = {"addBook"})
    public Book addBook(String name) {
        final Book book = new Book(name);
        final Book savedBook = bookRepository.save(book);
        return savedBook;
    }


    //    @ShellMethod(value = "Get all books", key = {"books"})
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    //    @ShellMethod(value = "Delete book by id (long id)", key = {"deleteBook"})
    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book addAuthor(long bookId, long authorId) {
        Book book = bookRepository.getOne(bookId);
        Author author = authorRepository.getOne(authorId);
        book.getAuthors().add(author);
        return book;
    }

    @Transactional
    public Book removeAuthor(long bookId, long authorId) {
        Book book = bookRepository.getOne(bookId);
        Author author = authorRepository.getOne(authorId);
        book.getAuthors().remove(author);
        return book;
    }

    @Transactional
    public Book addGenre(long bookId, long genreId) {
        Book book = bookRepository.getOne(bookId);
        Genre genre = genreRepository.getOne(genreId);
        book.getGenres().add(genre);
        return book;
    }

    @Transactional
    public Book removeGenre(long bookId, long genreId) {
        Book book = bookRepository.getOne(bookId);
        Genre genre = genreRepository.getOne(genreId);
        book.getGenres().remove(genre);
        return book;
    }
}
