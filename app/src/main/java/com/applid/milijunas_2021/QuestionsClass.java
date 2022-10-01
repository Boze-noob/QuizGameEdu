package com.applid.milijunas_2021;

import java.util.Map;


public class QuestionsClass {
    Map<String,String> answer;
    String correctAnswer;
    String question;

    public QuestionsClass() {
    }

    public Map<String, String> getAnswer() {

        return answer;
    }

    public void setAnswer(Map<String, String> answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
