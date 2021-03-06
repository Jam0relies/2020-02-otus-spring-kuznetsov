package ru.otus.homework09.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework09.domain.Book;
import ru.otus.homework09.service.BookService;

@Controller
public class BooksPageController {
    private final BookService bookService;

    public BooksPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String bookPage(Model model) {
        model.addAttribute("books", bookService.getAll());
        model.addAttribute("bookToAdd", new Book());
        return "books";
    }

    @PostMapping("/books/{bookId}/delete")
    public String delete(@PathVariable long bookId) {
        bookService.delete(bookId);
        return "redirect:/books";
    }

    @PostMapping("/books")
    public String add(@ModelAttribute("bookToAdd") Book bookToAdd) {
        bookService.addBook(bookToAdd.getName());
        return "redirect:/books";
    }
}
