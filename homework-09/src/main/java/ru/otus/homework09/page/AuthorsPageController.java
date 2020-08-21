package ru.otus.homework09.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.homework09.service.AuthorService;

@Controller
public class AuthorsPageController {
    private final AuthorService authorService;

    public AuthorsPageController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("authors")
    public String authorsPage(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authors";
    }
}
