package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionImpl implements Question {
    private String text;
    private String answer;

    @Override
    public boolean isCorrectAnswer(String answer) {
        return this.answer.equals(answer.trim());
    }
}
