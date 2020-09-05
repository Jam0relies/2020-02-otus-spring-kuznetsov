package ru.otus.homework11.rest;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import ru.otus.homework11.domain.Author;
import ru.otus.homework11.domain.Book;
import ru.otus.homework11.domain.BookInfoProjection;
import ru.otus.homework11.domain.Genre;
import ru.otus.homework11.repository.AuthorRepository;
import ru.otus.homework11.repository.BookRepository;
import ru.otus.homework11.repository.GenreRepository;
import ru.otus.homework11.rest.dto.AddBookRequestDto;
import ru.otus.homework11.rest.dto.AuthorDto;
import ru.otus.homework11.rest.dto.BookDto;
import ru.otus.homework11.rest.dto.GenreDto;

import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class BookEndpoints {
    @Bean
    public RouterFunction<ServerResponse> bookRoutes(BookRepository bookRepository, ModelMapper modelMapper, AuthorRepository authorRepository, GenreRepository genreRepository) {
        final Function<Object, BookDto> bookInfoToDtoMapper = book -> modelMapper.map(book, BookDto.class);
        final Function<Author, AuthorDto> authorToDtoMapper = (Author author) -> modelMapper.map(author, AuthorDto.class);
        final Function<Genre, GenreDto> genreToDtoMapper = (Genre genre) -> modelMapper.map(genre, GenreDto.class);
        return route()
                .GET("/api/books",
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(bookRepository.findAllBookInfosBy()
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
                .GET("/api/books/{bookId}/authors",
                        request -> ok().body(bookRepository.findBookInfoById(request.pathVariable("bookId"))
                                .map(BookInfoProjection::getAuthors)
                                .flatMapIterable(authors -> authors)
                                .map(authorToDtoMapper), BookDto.class))
                .GET("/api/books/{bookId}/authors-to-add", request -> {
                    Flux<String> ids = bookRepository.findBookInfoById(request.pathVariable("bookId"))
                            .map(BookInfoProjection::getAuthors)
                            .flatMapIterable(authors -> authors).map(Author::getId);
                    return ok().body(authorRepository.findByIdNotIn(ids).map(bookInfoToDtoMapper), BookDto.class);
                })
                .DELETE("/api/books/{bookId}/authors/{authorId}",
                        request ->
                                bookRepository.removeAuthorFromBook(request.pathVariable("bookId"),
                                        request.pathVariable("authorId"))
                                        .flatMap(r -> ok().build()))
                .POST("/api/books/{bookId}/authors/{authorId}",
                        request -> bookRepository.addAuthorToBook(request.pathVariable("bookId"), request.pathVariable("authorId"))
                                .flatMap(result -> ok().build()))
                .GET("/api/books/{bookId}/authors",
                        request -> ok().body(bookRepository.findBookInfoById(request.pathVariable("bookId"))
                                .map(BookInfoProjection::getAuthors)
                                .flatMapIterable(authors -> authors)
                                .map(authorToDtoMapper), BookDto.class))
                .GET("/api/books/{bookId}/genres-to-add", request -> {
                    Flux<String> ids = bookRepository.findBookInfoById(request.pathVariable("bookId"))
                            .map(BookInfoProjection::getGenres)
                            .flatMapIterable(genres -> genres).map(Genre::getId);
                    return ok().body(genreRepository.findByIdNotIn(ids).map(bookInfoToDtoMapper), BookDto.class);
                })
                .DELETE("/api/books/{bookId}/genres/{genreId}",
                        request ->
                                bookRepository.removeGenreFromBook(request.pathVariable("bookId"),
                                        request.pathVariable("genreId"))
                                        .flatMap(r -> ok().build()))
                .POST("/api/books/{bookId}/genres/{genreId}",
                        request -> bookRepository.addGenreToBook(request.pathVariable("bookId"), request.pathVariable("genreId"))
                                .flatMap(result -> ok().build()))
                .GET("/api/books/{bookId}/genres",
                        request -> ok().body(bookRepository.findBookInfoById(request.pathVariable("bookId"))
                                .map(BookInfoProjection::getGenres)
                                .flatMapIterable(genres -> genres)
                                .map(genreToDtoMapper), BookDto.class))
                .build();
    }
}
