package com.tohoieva.mathtests.addTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class AddTopicController {

    @FXML
    private TextField topicField;

    private Consumer<String> callback;

    public void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    @FXML
    private void handleAddButtonAdd(ActionEvent event) {

        String newTopic = topicField.getText();

        if (callback != null) {
            callback.accept(newTopic);
        }

        // Закрити вікно "AddTopicPage"
        ((Stage) topicField.getScene().getWindow()).close();
    }

    @FXML
    private void handleAddButtonExit(ActionEvent event) {

        // Закрити вікно "AddTopicPage"
        ((Stage) topicField.getScene().getWindow()).close();
    }
}
