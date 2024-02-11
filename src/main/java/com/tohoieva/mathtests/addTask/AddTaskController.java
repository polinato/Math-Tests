package com.tohoieva.mathtests.addTask;

import com.tohoieva.mathtests.MathTests;
import com.tohoieva.mathtests.configuration.DOMManager;
import com.tohoieva.mathtests.configuration.Task;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class AddTaskController extends MathTests implements Initializable {

    private final ArrayList<Task> writtenTask = new ArrayList<>();
    private DOMManager domManager = new DOMManager("tasks", "task");
    private final File file = new File("tasks.xml");

    ObservableList<String> levels = FXCollections.observableArrayList("Легкий", "Середній", "Складний");
    SpinnerValueFactory<Integer> spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 11);
    String addNewTopic = "Додати нову тему";
    ObservableList<String> topics = FXCollections.observableArrayList();

    @FXML
    protected TextArea questionArea;

    @FXML
    protected TextField answerField;

    @FXML
    protected Spinner<Integer> gradeSpinner;

    @FXML
    protected ChoiceBox<String> topicList;

    @FXML
    protected ChoiceBox<String> levelList;

    @FXML
    protected AnchorPane anchorPane;

    public void handleButtonToMainManu(ActionEvent actionEvent) throws IOException {

        MathTests.openWindow("mainMenu/MainMenuPage.fxml", anchorPane);
    }

    public void handleButtonSaveQuestion(ActionEvent actionEvent) /*throws IOException*/ {

        String question = questionArea.getText();
        String answer = answerField.getText();
        String grade = String.valueOf(gradeSpinner.getValue());
        String topic = topicList.getValue();
        String level = levelList.getValue();

        writtenTask.add(new Task(question, answer, grade, topic, level));
        domManager = new DOMManager(writtenTask, "tasks", "task");
        domManager.writeXml(file);

        setValues();
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        setValues();
        topicList.setOnAction(event -> {
            String selectedTopic = topicList.getValue();
            if (selectedTopic != null && selectedTopic.equals(addNewTopic)) {
                try {
                    // Створити новий FXMLLoader та завантажити FXML-файл вікна "AddTopicPage"
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AddTopicPage.fxml"));
                    Parent root = loader.load();

                    // Отримати контролер вікна "AddTopicPage"
                    AddTopicController controller = loader.getController();
                    topicList.setValue(topics.get(0));

                    // Створити callback-функцію для обробки результату (нової теми)
                    Consumer<String> callback = new Consumer<String>() {
                        @Override
                        public void accept(String newTopic) {

                            if (newTopic.length() == 0) {
                                if (topics.size() != 0) {
                                    topicList.setValue(topics.get(0));
                                }
                            }
                            else if (!topics.contains(newTopic)) {
                                topics.add(newTopic);
                                topicList.setItems(topics);
                            }
                            else {
                                topicList.setValue(newTopic);
                            }
                        }
                    };

                    // Передати callback-функцію до контролера "AddTopicPageController"
                    controller.setCallback(callback);

                    // Створити новий Stage для вікна "AddTopicPage"
                    Stage addTopicStage = new Stage();
                    addTopicStage.initModality(Modality.APPLICATION_MODAL);
                    addTopicStage.setTitle("Додати тему");
                    addTopicStage.setScene(new Scene(root));

                    // Показати вікно "AddTopicPage" та очікувати його закриття
                    addTopicStage.showAndWait();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void setValues() {

        questionArea.setText("");
        answerField.setText("");

        levelList.setValue(levels.get(0));
        levelList.setItems(levels);

        spinner.setValue(6);
        gradeSpinner.setValueFactory(spinner);

        //System.out.println(file.toPath());

        if (file.exists()) {

            ArrayList<Task> tasksList = domManager.readXml(file);
            topics = getDistinctTopics(tasksList);
            topicList.setItems(topics);
            topicList.setValue(topics.get(0));
            //topicList.setItems(topics);
        }

        topics.add(addNewTopic);
        topicList.setItems(topics);
    }

    private ObservableList<String> getDistinctTopics(ArrayList<Task> tasksList) {

        ObservableList<String> topics = FXCollections.observableArrayList();
        for (Task task : tasksList) {
            if (!topics.contains(task.getTopic())) {
                topics.add(task.getTopic());
            }
        }
        return topics;
    }
}
