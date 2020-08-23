package ru.otus.homework10.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework10.domain.Author;
import ru.otus.homework10.service.AuthorService;

@Controller
public class AuthorsPageController {
    private final AuthorService authorService;

    public AuthorsPageController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authorsPage(Model model) {
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("authorToAdd", new Author());
        return "authors";
    }

    @PostMapping("/authors/{authorId}/delete")
    public String delete(@PathVariable long authorId) {
        authorService.delete(authorId);
        return "redirect:/authors";
    }

    @PostMapping("/authors")
    public String add(@ModelAttribute("authorToAdd") Author authorToAdd) {
        authorService.addAuthor(authorToAdd.getName());
        return "redirect:/authors";
    }
}
