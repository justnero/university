package ru.justnero.study.dsmnm.lab01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("МиСХИ ЛР №1 Куркчи А. Э.");
        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }
}
