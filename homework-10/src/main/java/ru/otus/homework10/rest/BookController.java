package ru.otus.homework10.rest;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.homework10.domain.Book;
import ru.otus.homework10.service.BookService;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/api/books")
    public List<Book> getAllBooks() {
        return null;
    }
}
