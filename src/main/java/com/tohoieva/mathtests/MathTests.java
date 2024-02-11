package com.tohoieva.mathtests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MathTests extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(MathTests.class.getResource("MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Math Tests");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openWindow(String name, Pane pane) throws IOException{

        Parent root = FXMLLoader.load(MathTests.class.getResource("/com/tohoieva/mathtests/" + name));
        Stage newStage = (Stage) pane.getScene().getWindow();
        newStage.setTitle("Math Tests");
        newStage.setScene(new Scene(root, 600, 400));
        newStage.show();
    }
}