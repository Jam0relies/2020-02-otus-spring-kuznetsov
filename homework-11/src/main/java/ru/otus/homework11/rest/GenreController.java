package ru.otus.homework11.rest;

//
//@RestController
//public class GenreController {
//    private final GenreService genreService;
//    private final ModelMapper modelMapper;
//
//    public GenreController(GenreService genreService, ModelMapper modelMapper) {
//        this.genreService = genreService;
//        this.modelMapper = modelMapper;
//    }
//
//    @GetMapping("/api/genres")
//    public List<GenreDto> getAllGenres() {
//        return genreService.getAll().stream().map(a -> modelMapper.map(a, GenreDto.class)).collect(Collectors.toList());
//    }
//
//    @DeleteMapping("/api/genres/{genreId}")
//    @ResponseStatus(value = HttpStatus.OK)
//    public void delete(@PathVariable long genreId) {
//        genreService.delete(genreId);
//    }
//
//    @PostMapping("/api/genres")
//    public GenreDto addAuthor(@RequestBody @Valid AddGenreRequestDto genreToAdd) {
//        Genre genre = genreService.addGenre(genreToAdd.getName());
//        return modelMapper.map(genre, GenreDto.class);
//    }
//}
