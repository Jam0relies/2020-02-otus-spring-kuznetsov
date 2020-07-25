package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.config.QuizConfig;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Locale;

@ShellComponent
@RequiredArgsConstructor
public class QuizServiceShell {
    private final QuestionDao questionDao;
    private final MessageSource messageSource;
    private final Locale locale;
    private final QuizConfig quizConfig;
    private String username;
    private List<Question> questions;
    private int questionIndex = 0;
    private int correctAnswersCount = 0;

    @EventListener
//    @ShellMethod()
    public void printInvite(ApplicationStartedEvent event) {
        System.out.println(messageSource.getMessage("message.ask_name", null, locale));
    }

    @ShellMethod(value = "Set name command", key = {"name", "n"})
    public String setName(@ShellOption(defaultValue = "anonymous") String username) {
        this.username = username;
        return messageSource.getMessage("message.invite", new String[]{this.username}, locale);
    }

    @ShellMethod(value = "Start quiz", key = {"start", "s"})
    @ShellMethodAvailability(value = "isQuizAvailable")
    public String startQuiz() {
        questions = questionDao.getAll();
        return getQuestionText();
    }

    @ShellMethod(value = "Enter answer", key = {"answer", "a"})
    @ShellMethodAvailability(value = {"isQuizStarted"})
    public String answer(@ShellOption() String answer) {
        Question answeredQuestion = questions.get(questionIndex++);
        if (answeredQuestion.isCorrectAnswer(answer)) {
            correctAnswersCount++;
        }
        if (questionIndex < questions.size()) {
            return getQuestionText();
        } else {
            String finalMessage = messageSource.getMessage("message.end",
                    new String[]{username, Integer.toString(correctAnswersCount)}, locale) + "\n";
            if (correctAnswersCount >= quizConfig.getPassingScore()) {
                finalMessage += messageSource.getMessage("message.successful", null, locale);
            } else {
                finalMessage += messageSource.getMessage("message.fail", null, locale);
            }
            questions = null;
            return finalMessage;
        }
    }

    private String getQuestionText() {
        Question nextQuestion = questions.get(questionIndex);
        return nextQuestion.getText() + "\n" +
                messageSource.getMessage("message.answer_tip", null, locale);
    }

    public Availability isQuizAvailable() {
        return username == null ?
                Availability.unavailable(messageSource.getMessage("message.ask_name", null, locale)) :
                Availability.available();
    }

    public Availability isQuizStarted() {
        Availability quizAvailability = isQuizAvailable();
        if (!quizAvailability.isAvailable()) {
            return quizAvailability;
        }
        return questions == null ?
                Availability.unavailable(messageSource.getMessage("message.invite", new String[]{this.username}, locale)) :
                Availability.available();
    }
}
