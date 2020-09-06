package ru.otus.homework10.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework10.domain.Book;
import ru.otus.homework10.rest.dto.AddBookRequestDto;
import ru.otus.homework10.rest.dto.BookDto;
import ru.otus.homework10.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
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
    public BookDto addAuthor(@RequestBody @Valid AddBookRequestDto bookToAdd) {
        Book genre = bookService.addBook(bookToAdd.getName());
        return modelMapper.map(genre, BookDto.class);
    }
}
