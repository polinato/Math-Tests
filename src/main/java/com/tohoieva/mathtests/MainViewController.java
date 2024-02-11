package com.tohoieva.mathtests;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainViewController {

    @FXML
    private BorderPane borderPane;

    public void initialize () {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tohoieva/mathtests/mainMenu/MainMenuPage.fxml"));
            borderPane.setCenter(fxmlLoader.load());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}