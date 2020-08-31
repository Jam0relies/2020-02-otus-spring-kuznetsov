package ru.otus.homework11.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.rest.dto.AddBookRequestDto;
import ru.otus.homework11.rest.dto.AuthorDto;
import ru.otus.homework11.rest.dto.BookDto;
import ru.otus.homework11.rest.dto.GenreDto;
import ru.otus.homework11.service.AuthorService;
import ru.otus.homework11.service.BookService;
import ru.otus.homework11.service.GenreService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookController(BookService bookService, ModelMapper modelMapper, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookService.getAll().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{bookId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable long bookId) {
        bookService.delete(bookId);
    }

    @PostMapping("/api/books")
    public BookDto addBook(@RequestBody @Valid AddBookRequestDto bookToAdd) {
        Book book = bookService.addBook(bookToAdd.getName());
        return modelMapper.map(book, BookDto.class);
    }

    @GetMapping("/api/books/{bookId}")
    public BookDto geBook(@PathVariable long bookId) {
        Book book = bookService.getById(bookId);
        return modelMapper.map(book, BookDto.class);
    }

    @DeleteMapping("/api/books/{bookId}/authors/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAuthor(@PathVariable long bookId, @PathVariable long authorId) {
        bookService.removeAuthor(bookId, authorId);
    }

    @GetMapping("/api/books/{bookId}/authors")
    public List<AuthorDto> getAuthors(@PathVariable long bookId) {
        Book book = bookService.getById(bookId);
        return book.getAuthors().stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{bookId}/authors-to-add")
    public List<AuthorDto> getPossibleAuthors(@PathVariable long bookId) {
        Book book = bookService.getById(bookId);
        List<Author> authors = authorService.getAll();
        authors.removeAll(book.getAuthors());
        return authors.stream().map(a -> modelMapper.map(a, AuthorDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/authors/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void addAuthorToBook(@PathVariable long bookId, @PathVariable long authorId) {
        bookService.addAuthor(bookId, authorId);
    }

    @DeleteMapping("/api/books/{bookId}/genres/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeGenre(@PathVariable long bookId, @PathVariable long genreId) {
        bookService.removeGenre(bookId, genreId);
    }

    @GetMapping("/api/books/{bookId}/genres")
    public List<GenreDto> getGenres(@PathVariable long bookId) {
        Book book = bookService.getById(bookId);
        return book.getGenres().stream().map(g -> modelMapper.map(g, GenreDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{bookId}/genres-to-add")
    public List<GenreDto> getPossibleGenres(@PathVariable long bookId) {
        Book book = bookService.getById(bookId);
        List<Genre> genres = genreService.getAll();
        genres.removeAll(book.getGenres());
        return genres.stream().map(g -> modelMapper.map(g, GenreDto.class)).collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/genres/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public void addGenreToBook(@PathVariable long bookId, @PathVariable long genreId) {
        bookService.addGenre(bookId, genreId);
    }
}
