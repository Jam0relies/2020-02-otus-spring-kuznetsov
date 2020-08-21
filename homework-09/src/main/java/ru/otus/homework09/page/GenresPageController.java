package ru.otus.homework09.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.service.GenreService;

@Controller
public class GenresPageController {
    private final GenreService genreService;

    public GenresPageController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genres";
    }
}
