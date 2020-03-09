package ru.otus.domain;

import lombok.Data;

@Data
public class QuestionImpl implements Question {
    private final String text;
    private final String answer;

    @Override
    public boolean isCorrectAnswer(String answer) {
        return this.answer.equals(answer.trim());
    }
}
