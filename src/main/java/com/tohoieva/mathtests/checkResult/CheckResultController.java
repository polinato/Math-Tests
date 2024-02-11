package com.tohoieva.mathtests.checkResult;

import com.tohoieva.mathtests.MathTests;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CheckResultController {

    @FXML
    protected AnchorPane anchorPane;

    @FXML
    protected Label rightAnswersLabel;

    @FXML
    protected Label wrongAnswersLabel;

    @FXML
    private Label allAnswersLabel;

    public void handleActionToMainMenu(ActionEvent actionEvent) throws IOException {

        MathTests.openWindow("mainMenu/MainMenuPage.fxml", anchorPane);
    }

    public void handleActionPassTest(ActionEvent actionEvent) throws IOException {

        //save fields' value
        MathTests.openWindow("passTest/PassTestPage.fxml", anchorPane);
    }

    public void handleActionExit(ActionEvent actionEvent) {

        Platform.exit();
    }

    public void initializeController(Integer selectedAmount, int correctAnswersCount) {

        rightAnswersLabel.setText(String.valueOf(correctAnswersCount));
        wrongAnswersLabel.setText(String.valueOf(selectedAmount - correctAnswersCount));
        allAnswersLabel.setText(String.valueOf(selectedAmount));
    }
}
