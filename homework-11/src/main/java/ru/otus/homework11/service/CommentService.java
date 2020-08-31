package ru.otus.homework11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.CommentRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public String getById(long id) {
        return commentRepository.findById(id).toString();
    }

    @Transactional(readOnly = true)
    public List<Comment> getByBookId(long bookId) {
        Book book = bookRepository.getOne(bookId);
        return book.getComments();
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
