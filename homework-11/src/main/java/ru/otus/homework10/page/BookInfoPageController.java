//package ru.otus.homework10.page;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import ru.otus.homework10.domain.Author;
//import ru.otus.homework10.domain.Book;
//import ru.otus.homework10.domain.Comment;
//import ru.otus.homework10.domain.Genre;
//import ru.otus.homework10.service.AuthorService;
//import ru.otus.homework10.service.BookService;
//import ru.otus.homework10.service.CommentService;
//import ru.otus.homework10.service.GenreService;
//
//import java.util.List;
//
//@Controller
//public class BookInfoPageController {
//    private final BookService bookService;
//    private final GenreService genreService;
//    private final AuthorService authorService;
//    private final CommentService commentService;
//
//    public BookInfoPageController(BookService bookService, GenreService genreService, AuthorService authorService, CommentService commentService) {
//        this.bookService = bookService;
//        this.genreService = genreService;
//        this.authorService = authorService;
//        this.commentService = commentService;
//    }
//
//    @GetMapping("/books/{bookId}")
//    public String bookInfoPage(@PathVariable long bookId, Model model) {
//        Book book = bookService.getById(bookId);
//        model.addAttribute("book", book);
//        List<Author> authors = authorService.getAll();
//        authors.removeAll(book.getAuthors());
//        model.addAttribute("authors", authors);
//        model.addAttribute("authorToAdd", new Author());
//        List<Genre> genres = genreService.getAll();
//        genres.removeAll(book.getGenres());
//        model.addAttribute("genres", genres);
//        model.addAttribute("genreToAdd", new Genre());
//        model.addAttribute("commentToAdd", new Comment());
//        return "book-info";
//    }
//
//    @PostMapping("/books/{bookId}/authors/{authorId}/remove")
//    public String removeAuthor(@PathVariable long bookId, @PathVariable long authorId) {
//        bookService.removeAuthor(bookId, authorId);
//        return "redirect:/books/" + bookId;
//    }
//
//    @PostMapping("/books/{bookId}/genres/{genresId}/remove")
//    public String removeGenre(@PathVariable long bookId, @PathVariable long genresId) {
//        bookService.removeGenre(bookId, genresId);
//        return "redirect:/books/" + bookId;
//    }
//
//    @PostMapping("/books/{bookId}/genres")
//    public String addGenre(@PathVariable long bookId, @ModelAttribute("genreToAdd") Genre genreToAdd) {
//        bookService.addGenre(bookId, genreToAdd.getId());
//        return "redirect:/books/" + bookId;
//    }
//
//    @PostMapping("/books/{bookId}/authors")
//    public String addAuthor(@PathVariable long bookId, @ModelAttribute("authorToAdd") Author genreToAdd) {
//        bookService.addAuthor(bookId, genreToAdd.getId());
//        return "redirect:/books/" + bookId;
//    }
//
//    @PostMapping("/books/{bookId}/comments")
//    public String addComment(@PathVariable long bookId, @ModelAttribute("commentToAdd") Comment comment) {
//        commentService.addComment(bookId, comment.getText());
//        return "redirect:/books/" + bookId;
//    }
//}
