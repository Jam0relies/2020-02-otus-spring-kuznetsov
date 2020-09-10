//package ru.otus.homework12.rest;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import ru.otus.homework12.domain.Genre;
//import ru.otus.homework12.repository.GenreRepository;
//import ru.otus.homework12.rest.dto.AddGenreRequestDto;
//import ru.otus.homework12.rest.dto.GenreDto;
//
//import java.util.function.Function;
//
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//
//@Configuration
//public class GenreEndpoints {
//    @Bean
//    public RouterFunction<ServerResponse> genreRoutes(GenreRepository genreRepository, ModelMapper modelMapper) {
//        final Function<Genre, GenreDto> genreToDtoMapper = (Genre genre) -> modelMapper.map(genre, GenreDto.class);
//        return route()
//                .GET("/api/genres",
//                        request -> ok().contentType(APPLICATION_JSON)
//                                .body(genreRepository.findAll()
//                                        .map(genreToDtoMapper), GenreDto.class))
//                .DELETE("/api/genres/{genreId}",
//                        request -> genreRepository.deleteById(request.pathVariable("genreId"))
//                                .flatMap(v -> ok().build()))
//                .POST("/api/genres", accept(APPLICATION_JSON),
//                        request -> request.bodyToMono(AddGenreRequestDto.class)
//                                .map(dto -> {
//                                    Genre genre = new Genre(dto.getName());
//                                    return genreRepository.save(genre).map(genreToDtoMapper);
//                                })
//                                .flatMap(result -> ok().body(result, GenreDto.class)))
//                .build();
//    }
//}
