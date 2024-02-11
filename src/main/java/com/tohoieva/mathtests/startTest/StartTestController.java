package com.tohoieva.mathtests.startTest;

import com.tohoieva.mathtests.MathTests;
import com.tohoieva.mathtests.checkResult.CheckResultController;
import com.tohoieva.mathtests.configuration.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartTestController {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    protected Label label;

    @FXML
    public TextArea questionArea;

    @FXML
    public TextField answerField;

    private List<Task> selectedTasks;

    protected String selectedTopic;
    protected String selectedLever;
    protected String selectedGrade;
    protected Integer selectedAmount;

    private int currentQuestionIndex;
    private int correctAnswersCount;

    public void initializeController(List<Task> tasks, String topic, String grade, String level, int amount) {

        this.selectedTopic = topic;
        this.selectedLever = level;
        this.selectedGrade = grade;
        this.selectedAmount = amount;
        currentQuestionIndex = 0;
        correctAnswersCount = 0;
        this.selectedTasks = createTestSet(tasks, topic, grade, level, amount);
        showQuestion();
    }

    public void handleActionGoNext(ActionEvent actionEvent) throws IOException {

        String userAnswer = answerField.getText();

        if (isCorrectAnswer(userAnswer)) {
            correctAnswersCount++;
        }

        if (currentQuestionIndex < selectedTasks.size() - 1) {
            currentQuestionIndex++;
            showQuestion();
        }
        else {
            checkResults();
        }
    }

    public void handleActionGoToMainMenu(ActionEvent actionEvent) throws IOException {
        MathTests.openWindow("mainMenu/MainMenuPage.fxml", anchorPane);
    }

    public static List<Task> createTestSet(List<Task> tasks, String topic, String grade, String level, int amount) {

        List<Task> selectedTasks = new ArrayList<>();

        // Фільтруємо завдання за вказаними критеріями
        for (Task task : tasks) {
            if (task.getTopic().equalsIgnoreCase(topic)
                    && task.getGrade().equalsIgnoreCase(grade)
                    && task.getLevel().equalsIgnoreCase(level)) {
                selectedTasks.add(task);
            }
        }

        // Перемішуємо завдання
        Collections.shuffle(selectedTasks);

        // Вибираємо задану кількість питань
        if (selectedTasks.size() > amount) {
            System.out.println(amount);
            selectedTasks = selectedTasks.subList(0, amount);
        }

        return selectedTasks;
    }

    public void checkResults() throws IOException {

        FXMLLoader loader = new FXMLLoader(MathTests.class.getResource("/com/tohoieva/mathtests/checkResult/CheckResultPage.fxml"));
        Parent root = loader.load();
        CheckResultController checkResultController = loader.getController();
        checkResultController.initializeController(selectedAmount, correctAnswersCount);

        Stage newStage = (Stage) anchorPane.getScene().getWindow();
        newStage.setTitle("Math Tests");
        newStage.setScene(new Scene(root, 600, 400));
        newStage.show();
    }

    private void showQuestion() {

        Task currentQuestion = selectedTasks.get(currentQuestionIndex);
        questionArea.setText(currentQuestion.getQuestion());
        answerField.clear();
        label.setText("Питання №" + String.valueOf(currentQuestionIndex + 1));
    }

    private boolean isCorrectAnswer(String userAnswer) {

        Task currentQuestion = selectedTasks.get(currentQuestionIndex);
        return userAnswer.equalsIgnoreCase(currentQuestion.getAnswer());
    }
}
