package ru.otus.homework11.rest;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.homework11.domain.Comment;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.rest.dto.BookDto;
import ru.otus.homework11.rest.dto.CommentDto;
import ru.otus.homework11.rest.dto.PostCommentDto;

import java.time.Instant;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class CommentEndpoints {
    @Bean
    public RouterFunction<ServerResponse> commentRoutes(BookRepository bookRepository, ModelMapper modelMapper) {
        final Function<Comment, CommentDto> commentToDtoMapper = comment -> modelMapper.map(comment, CommentDto.class);
        return route()
                .GET("/api/books/{bookId}/comments",
                        request -> ok().body(bookRepository.getAllComments(request.pathVariable("bookId"))
                                .map(commentToDtoMapper), BookDto.class))
                .POST("/api/books/{bookId}/comments",
                        request -> request.bodyToMono(PostCommentDto.class)
                                .flatMap(dto -> {
                                    Comment comment = new Comment(dto.getText(), Instant.now());
                                    return bookRepository.addComment(request.pathVariable("bookId"), comment)
                                            .flatMap(result -> ok().build());
                                }))
                .build();
    }
}
