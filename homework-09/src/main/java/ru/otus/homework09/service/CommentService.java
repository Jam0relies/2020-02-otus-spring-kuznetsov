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

    public String getById(long id) {
        return commentRepository.findById(id).toString();
    }

    @Transactional(readOnly = true)
    public String getByBookId(long bookId) {
        Book book = bookRepository.getOne(bookId);
        return book.getComments().toString();
    }

    @Transactional
    public String addComment(long bookId, String text) {
        Book book = bookRepository.getOne(bookId);
        return commentRepository.save(new Comment(text, Instant.now(), book)).toString();
    }

    public void delete(long id) {
        commentRepository.deleteById(id);
    }
}
