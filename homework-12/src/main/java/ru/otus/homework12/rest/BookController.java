package ru.otus.homework12.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework12.domain.Author;
import ru.otus.homework12.domain.Book;
import ru.otus.homework12.domain.Genre;
import ru.otus.homework12.repository.AuthorRepository;
import ru.otus.homework12.repository.BookRepository;
import ru.otus.homework12.repository.GenreRepository;
import ru.otus.homework12.rest.dto.AddBookRequestDto;
import ru.otus.homework12.rest.dto.AuthorDto;
import ru.otus.homework12.rest.dto.BookDto;
import ru.otus.homework12.rest.dto.GenreDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    final Function<Object, BookDto> bookInfoToDtoMapper;
    final Function<Author, AuthorDto> authorToDtoMapper;
    final Function<Genre, GenreDto> genreToDtoMapper;

    public BookController(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
        bookInfoToDtoMapper = book -> modelMapper.map(book, BookDto.class);
        authorToDtoMapper = (Author author) -> modelMapper.map(author, AuthorDto.class);
        genreToDtoMapper = (Genre genre) -> modelMapper.map(genre, GenreDto.class);
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/api/books")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(b -> modelMapper.map(b, BookDto.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{bookId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable String bookId) {
        bookRepository.deleteById(bookId);
    }

    @PostMapping("/api/books")
    public BookDto addBook(@RequestBody @Valid AddBookRequestDto bookToAdd) {
        Book genre = bookRepository.save(new Book(bookToAdd.getName()));
        return modelMapper.map(genre, BookDto.class);
    }

    @GetMapping("/api/books/{bookId}")
    public BookDto getBook(@PathVariable String bookId) {
        return bookRepository.findById(bookId).map(bookInfoToDtoMapper).get();
    }

    @GetMapping("/api/books/{bookId}/authors")
    public List<AuthorDto> getBookAuthors(@PathVariable String bookId) {
        return bookRepository.findBookInfoById(bookId).getAuthors().stream()
                .map(authorToDtoMapper).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{bookId}/authors-to-add")
    public List<AuthorDto> getPossibleAuthors(@PathVariable String bookId) {
        Set<String> authorIds = bookRepository.findBookInfoById(bookId).getAuthors()
                .stream().map(Author::getId).collect(Collectors.toSet());
        return authorRepository.findByIdNotIn(authorIds).stream().map(authorToDtoMapper).collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{bookId}/authors/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeAuthorFromBook(@PathVariable String bookId, @PathVariable String authorId) {
        bookRepository.removeAuthorFromBook(bookId, authorId);
    }

    @PostMapping("/api/books/{bookId}/authors/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public void addAuthorToBook(@PathVariable String bookId, @PathVariable String authorId) {
        bookRepository.addAuthorToBook(bookId, authorId);
    }

    @GetMapping("/api/books/{bookId}/genres")
    public List<GenreDto> getBookGenres(@PathVariable String bookId) {
        return bookRepository.findBookInfoById(bookId).getGenres().stream().
                map(genreToDtoMapper).collect(Collectors.toList());
    }

    @GetMapping("/api/books/{bookId}/genres-to-add")
    public List<GenreDto> getPossibleGenres(@PathVariable String bookId) {
        Set<String> genreIds = bookRepository.findBookInfoById(bookId).getGenres()
                .stream().map(Genre::getId).collect(Collectors.toSet());
        return genreRepository.findByIdNotIn(genreIds).stream().map(genreToDtoMapper).collect(Collectors.toList());
    }

    @PostMapping("/api/books/{bookId}/genres/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public void addGenreToBook(@PathVariable String bookId, @PathVariable String genreId) {
        bookRepository.addGenreToBook(bookId, genreId);
    }

    @DeleteMapping("/api/books/{bookId}/genres/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeGenreFromBook(@PathVariable String bookId, @PathVariable String genreId) {
        bookRepository.removeGenreFromBook(bookId, genreId);
    }
}
