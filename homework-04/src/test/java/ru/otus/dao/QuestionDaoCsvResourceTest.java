package ru.otus.dao;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.junit.jupiter.api.Test;
import ru.otus.domain.Question;
import ru.otus.domain.QuestionImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionDaoCsvResourceTest {

    @Test
    void getAll() {
        List<Question> expectedQuestions = Arrays.asList(
                new QuestionImpl("question1", "answer1"),
                new QuestionImpl("question2", "answer2"),
                new QuestionImpl("question3", "answer3"),
                new QuestionImpl("question4", "answer4"),
                new QuestionImpl("question5", "answer5")
        );

        QuestionDao questionDao = new QuestionDaoCsvResource("testQuestions.csv", new CsvMapper());
        List<Question> questions = questionDao.getAll();
        assertEquals(expectedQuestions, questions);
    }

    @Test
    void unquotedAnswer() {
        List<Question> expectedQuestions = Arrays.asList(
                new QuestionImpl("question1", "1")
        );

        QuestionDao questionDao = new QuestionDaoCsvResource("unquotedAnswer.csv", new CsvMapper());
        List<Question> questions = questionDao.getAll();
        assertEquals(expectedQuestions, questions);
    }
}