package ru.otus.homework10.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework10.domain.Genre;
import ru.otus.homework10.service.GenreService;

@Controller
public class GenresPageController {
    private final GenreService genreService;

    public GenresPageController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("genreToAdd", new Genre());
        return "genres";
    }

    @PostMapping("/genres/{authorId}/delete")
    public String delete(@PathVariable long authorId) {
        genreService.delete(authorId);
        return "redirect:/authors";
    }

    @PostMapping("/genres")
    public String add(@ModelAttribute("genreToAdd") Genre authorToAdd) {
        genreService.addGenre(authorToAdd.getName());
        return "redirect:/genres";
    }
}
