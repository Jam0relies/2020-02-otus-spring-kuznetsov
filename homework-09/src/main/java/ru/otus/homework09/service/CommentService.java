package ru.otus.homework09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework09.domain.Book;
import ru.otus.homework09.domain.Comment;
import ru.otus.homework09.repository.BookRepository;
import ru.otus.homework09.repository.CommentRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    //    @ShellMethod(value = "Find comment by id (long id)", key = {"commentById"})
    public String getById(long id) {
        return commentRepository.findById(id).toString();
    }

    @Transactional(readOnly = true)
//    @ShellMethod(value = "Find comment by book id (long id)", key = {"commentsByBookId"})
    public String getByBookId(long bookId) {
        Book book = bookRepository.getOne(bookId);
        return book.getComments().toString();
    }

    @Transactional
//    @ShellMethod(value = "Add comment by book id (long bookId, String text)", key = {"addComment"})
    public String addComment(long bookId, String text) {
        Book book = bookRepository.getOne(bookId);
        return commentRepository.save(new Comment(text, Instant.now(), book)).toString();
    }

    //    @ShellMethod(value = "Delete comment by id (long id)", key = {"deleteComment"})
    public void delete(long id) {
        commentRepository.deleteById(id);
    }
}
