package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Scanner;

public class QuzServiceCommandLine implements QuizService {
    private final QuestionDao questionDao;

    public QuzServiceCommandLine(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public void startQuiz() {
        List<Question> questions = questionDao.getAll();
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter your name:");
            String studentName = scanner.nextLine();
            System.out.println(studentName + ", answer next questions:");
            int correctAnswersCount = 0;
            for (Question question : questions) {
                System.out.println(question.getText());
                String answer = scanner.nextLine();
                if (question.isCorrectAnswer(answer)) {
                    correctAnswersCount++;
                }
            }
            System.out.println(studentName + ", you answered correctly " + correctAnswersCount +
                    " questions.\n Quiz is over");
        }
    }
}
