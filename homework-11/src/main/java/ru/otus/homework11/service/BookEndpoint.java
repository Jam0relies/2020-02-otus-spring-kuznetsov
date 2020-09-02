package ru.otus.homework11.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.rest.dto.AddBookRequestDto;
import ru.otus.homework11.rest.dto.BookDto;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class BookEndpoint {
    @Bean
    public RouterFunction<ServerResponse> bookRoutes(BookRepository bookRepository, ModelMapper modelMapper) {
        final Function<Object, BookDto> bookInfoToDtoMapper = book -> modelMapper.map(book, BookDto.class);
        return route()
                .GET("/api/books",
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(bookRepository.findAllBookInfos()
                                        .map(bookInfoToDtoMapper), BookDto.class))
                .DELETE("/api/books/{bookId}",
                        request -> bookRepository.deleteById(request.pathVariable("bookId"))
                                .flatMap(v -> ok().build()))
                .POST("/api/books", accept(APPLICATION_JSON),
                        request -> request.bodyToMono(AddBookRequestDto.class)
                                .map(dto -> {
                                    Book book = new Book(dto.getName());
                                    return bookRepository.save(book).map(bookInfoToDtoMapper);
                                })
                                .flatMap(result -> ok().body(result, BookDto.class)))
                .GET("/api/books/{bookId}",
                        request -> ok().body(bookRepository.findBookInfoById(request.pathVariable("bookId"))
                                .map(bookInfoToDtoMapper), BookDto.class))

                .build();
    }
}
