package ru.justnero.study.sevsu.swt.lab13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Куркчи А. Э. ТПО-1.3");
        primaryStage.setScene(new Scene(root, 300, 140));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
