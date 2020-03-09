package ru.otus.dao;

import ru.otus.domain.Question;

import java.util.List;

public class QuestionDaoCsvResource implements QuestionDao {
    public QuestionDaoCsvResource(String resourceName) {

    }

    @Override
    public List<Question> getAll() {
        throw new RuntimeException("Not implemented");
    }
}
