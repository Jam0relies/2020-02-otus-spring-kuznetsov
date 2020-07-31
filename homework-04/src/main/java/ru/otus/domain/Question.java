package ru.otus.domain;

public interface Question {
    String getText();

    String getAnswer();

    boolean isCorrectAnswer(String answer);
}
