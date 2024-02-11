package com.tohoieva.mathtests.passTest;

import com.tohoieva.mathtests.MathTests;
import com.tohoieva.mathtests.configuration.DOMManager;
import com.tohoieva.mathtests.configuration.Task;
import com.tohoieva.mathtests.startTest.StartTestController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PassTestController extends MathTests implements Initializable {

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    protected ChoiceBox<String> gradeList;

    @FXML
    protected ChoiceBox<String> topicList;

    @FXML
    protected ChoiceBox<String> levelList;

    @FXML
    protected Spinner<Integer> amountSpinner;

    @FXML
    protected Button startButton;

    private final DOMManager domManager = new DOMManager("tasks", "task");
    private final File file = new File("tasks.xml");

    public void handleActionGoToMainMenu(ActionEvent actionEvent) throws IOException {

        MathTests.openWindow("mainMenu/MainMenuPage.fxml", anchorPane);
    }

    public void handleActionStartTest(ActionEvent actionEvent) throws IOException {

        Integer selectedAmount = amountSpinner.getValue();

        if (selectedAmount != 0) {

            String selectedTopic = String.valueOf(topicList.getValue());
            String selectedLevel = String.valueOf(levelList.getValue());
            String selectedGrade = String.valueOf(gradeList.getValue());
            ArrayList<Task> tasksList = domManager.readXml(file);

            FXMLLoader loader = new FXMLLoader(MathTests.class.getResource("/com/tohoieva/mathtests/startTest/StartTestPage.fxml"));
            Parent root = loader.load();
            StartTestController startTestController = loader.getController();
            startTestController.initializeController(tasksList, selectedTopic, selectedGrade, selectedLevel, selectedAmount);

            Stage newStage = (Stage) anchorPane.getScene().getWindow();
            newStage.setTitle("Math Tests");
            newStage.setScene(new Scene(root, 600, 400));
            newStage.show();
        }
        else {

            showAlert("Введена неправильна кількість тестів", "Ви вказали недійсну кількість питань. Змініть, будь ласка, налаштування тесту");
        }
    }

    @FXML
    private void updateTopics() {

        String selectedGrade = String.valueOf(gradeList.getValue());
        if (selectedGrade != null) {

            ArrayList<Task> tasksList = domManager.readXml(file);
            ObservableList<String> topics = getDistinctTopicsByGrade(tasksList, selectedGrade);
            topicList.setItems(topics);

            if (topicList.getItems().size() != 0) {
                topicList.setValue(topics.get(0));
                startButton.setDisable(false);
            }
            else {
                startButton.setDisable(true);
            }

            topicList.setDisable(false);
        }
    }

    private ObservableList<String> getDistinctTopicsByGrade(ArrayList<Task> tasksList, String grade) {

        ObservableList<String> topics = FXCollections.observableArrayList();

        for (Task task : tasksList) {

            if (task.getGrade().equals(grade) && !topics.contains(task.getTopic())) {

                topics.add(task.getTopic());
            }
        }
        return topics;
    }

    @FXML
    private void updateLevels() {

        String selectedTopic = String.valueOf(topicList.getValue());
        String selectedGrade = String.valueOf(gradeList.getValue());

        if (selectedTopic != null && selectedGrade != null) {

            ArrayList<Task> tasksList = domManager.readXml(file);
            ObservableList<String> levels = getDistinctLevelsByTopicAndGrade(tasksList, selectedTopic, selectedGrade);
            levelList.setItems(levels);

            if (levelList.getItems().size() != 0) {
                levelList.setValue(levels.get(0));
                startButton.setDisable(false);
            }
            else {
                startButton.setDisable(true);
            }
            levelList.setDisable(false);
            amountSpinner.setDisable(false);
        }
    }

    private ObservableList<String> getDistinctLevelsByTopicAndGrade(ArrayList<Task> tasksList, String topic, String grade) {

        ObservableList<String> levels = FXCollections.observableArrayList();
        for (Task task : tasksList) {
            if (task.getTopic().equals(topic) && task.getGrade().equals(grade) && !levels.contains(task.getLevel())) {
                levels.add(task.getLevel());
            }
        }
        return levels;
    }

    private int getAvailableTasksCount(ArrayList<Task> tasksList, String topic, String level, String grade) {

        return (int) tasksList.stream()
                .filter(task -> task.getTopic().equals(topic) && task.getLevel().equals(level) && task.getGrade().equals(grade))
                .count();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        startButton.setDisable(true);

        topicList.setDisable(true);
        levelList.setDisable(true);
        amountSpinner.setDisable(true);

        gradeList.setItems(setFromTo(1, 11));
        gradeList.setValue("Оберіть клас");
        gradeList.setOnAction(event -> updateTopics());
        topicList.setOnAction(event -> updateLevels());

        levelList.setOnAction(event -> {
            String selectedTopic = String.valueOf(topicList.getValue());
            String selectedLevel = String.valueOf(levelList.getValue());
            String selectedGrade = String.valueOf(gradeList.getValue());

            if (selectedTopic != null && !selectedTopic.equals("Виберіть тему")
                    && selectedLevel != null && !selectedLevel.equals("Виберіть рівень")) {

                ArrayList<Task> tasksList = domManager.readXml(file);

                int availableTasksCount = getAvailableTasksCount(tasksList, selectedTopic, selectedLevel, selectedGrade);

                if (availableTasksCount == 0) {

                    showAlert("Немає доступних задач", "У цієї теми та рівня немає задач. Будь ласка, виберіть іншу тему або рівень.");
                    amountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
                    startButton.setDisable(true);
                }
                else {
                   // showAlert("Доступні задачі", "Доступно задач: " + availableTasksCount);

                    SpinnerValueFactory<Integer> amountSpinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, availableTasksCount);
                    amountSpinnerFactory.setValue(availableTasksCount);
                    amountSpinner.setValueFactory(amountSpinnerFactory);

                    startButton.setDisable(false);
                }
            }
        });
    }

    private void showAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Повідомлення");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private ObservableList<String> setFromTo(Integer from, Integer to) {

        ObservableList<String> graduation = FXCollections.observableArrayList();
        for (int i = from; i < to; i++) {
            graduation.add(String.valueOf(i));
        }
        return graduation;
    }
}
