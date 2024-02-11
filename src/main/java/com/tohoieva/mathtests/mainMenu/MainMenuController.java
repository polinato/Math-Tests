package com.tohoieva.mathtests.mainMenu;

import com.tohoieva.mathtests.MathTests;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    protected Button AddTaskButton;

    @FXML
    protected Button PassTestButton;

    @FXML
    protected Button ExitButton;

    @FXML
    private AnchorPane anchorPane;

    public void handleActionAddTask(ActionEvent actionEvent) throws IOException {

        MathTests.openWindow("addTask/AddTaskPage.fxml", anchorPane);
    }

    public void handleActionPassTest(ActionEvent actionEvent) throws IOException {

        MathTests.openWindow("passTest/PassTestPage.fxml", anchorPane);
    }

    public void handleActionExit(ActionEvent actionEvent) {

        Platform.exit();
    }
}
