package ru.justnero.study.sevsu.java.lab05;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Main extends Application {

    private Stage primaryStage;

    private ObservableList<CD> cdData = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        stage.setTitle("SevSU semester.04 Java Lab.05");
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setScene(new Scene(root));

        initMainLayout();
    }

    public void initMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("main.fxml"));
            Scene scene = new Scene(loader.load());

            MainController mainController = loader.getController();
            mainController.setMain(this);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ObservableList<CD> getCDData() {
        return cdData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
