package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
public class QuizServiceCommandLine implements QuizService {
    private final QuestionDao questionDao;
    private final MessageSource messageSource;
    private final Locale locale;

    public QuizServiceCommandLine(QuestionDao questionDao, MessageSource messageSource, Locale locale) {
        this.questionDao = questionDao;
        this.messageSource = messageSource;
        this.locale = locale;
    }


    @Override
    public void startQuiz() {
        List<Question> questions = questionDao.getAll();
        try (Scanner scanner = new Scanner(System.in)) {
            String startMessage = messageSource.getMessage("message.ask_name", new String[0], locale);

            System.out.println(startMessage);
            String studentName = scanner.nextLine();
            String inviteMessage = messageSource.getMessage("message.invite", new String[]{studentName}, locale);
            System.out.println(inviteMessage);
            int correctAnswersCount = 0;
            for (Question question : questions) {
                System.out.println(question.getText());
                String answer = scanner.nextLine();
                if (question.isCorrectAnswer(answer)) {
                    correctAnswersCount++;
                }
            }
            String finalMessage = messageSource.getMessage("message.end",
                    new String[]{studentName, Integer.toString(correctAnswersCount)}, locale);
            System.out.println(finalMessage);
        }
    }
}
