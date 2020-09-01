//package ru.otus.homework11.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.otus.homework11.domain.Author;
//import ru.otus.homework11.repository.AuthorRepository;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AuthorService {
//    private final AuthorRepository authorRepository;
//
//    public String getById(Long id) {
//        return authorRepository.findById(id).toString();
//    }
//
//    public String getByName(String name) {
//        return authorRepository.findByName(name).toString();
//    }
//
//    public Author addAuthor(String name) {
//        final Author author = new Author(name);
//        final Author savedAuthor = authorRepository.save(author);
//        return savedAuthor;
//    }
//
//    public List<Author> getAll() {
//        return authorRepository.findAll();
//    }
//
//    public void delete(long id) {
//        authorRepository.deleteById(id);
//    }
//}
