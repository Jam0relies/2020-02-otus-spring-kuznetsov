package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Scanner;

@Service
public class QuizServiceCommandLine implements QuizService {
    private final QuestionDao questionDao;
    private final MessageSource messageSource;

    public QuizServiceCommandLine(QuestionDao questionDao, MessageSource messageSource) {
        this.questionDao = questionDao;
        this.messageSource = messageSource;
    }


    @Override
    public void startQuiz() {
        List<Question> questions = questionDao.getAll();
        try (Scanner scanner = new Scanner(System.in)) {
//            LocaleContextHolder.setLocale(Locale.ENGLISH);
            String startMessage = messageSource.getMessage("message.ask_name", new String[0], LocaleContextHolder.getLocale());

            System.out.println(startMessage);
            String studentName = scanner.nextLine();
            String inviteMessage = messageSource.getMessage("message.invite", new String[]{studentName}, LocaleContextHolder.getLocale());
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
                    new String[]{studentName, Integer.toString(correctAnswersCount)}, LocaleContextHolder.getLocale());
            System.out.println(finalMessage);
        }
    }
}
