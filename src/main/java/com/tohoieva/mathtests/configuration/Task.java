package com.tohoieva.mathtests.configuration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {

    private StringProperty question;
    private StringProperty answer;
    private StringProperty grade;
    private StringProperty topic;
    private StringProperty level;

    public Task(String question, String answer, String grade, String topic, String level) {

        this.question = new SimpleStringProperty(question);
        this.answer = new SimpleStringProperty(answer);
        this.grade = new SimpleStringProperty(grade);
        this.topic = new SimpleStringProperty(topic);
        this.level = new SimpleStringProperty(level);
    }

    public String getQuestion() {
        return question.get();
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public String getAnswer() {
        return answer.get();
    }

    public void setAnswer(String answer) {
        this.answer.set(answer);
    }

    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getLevel() {
        return level.get();
    }

    public void setLevel(String level) {
        this.level.set(level);
    }
}
