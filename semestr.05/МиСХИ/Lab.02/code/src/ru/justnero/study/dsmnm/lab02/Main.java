package ru.justnero.study.dsmnm.lab02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage testingStage;
    private Controller mainController;
    private StatsController statsController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("main.fxml").openStream());
        mainController = fxmlLoader.getController();
        mainController.setMain(this);

        primaryStage.setTitle("МиСХИ ЛР №2 Куркчи А. Э.");
        primaryStage.setScene(new Scene(fxmlLoader.getRoot(), 1024, 768));
        primaryStage.show();


        fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("stats.fxml").openStream());
        statsController = fxmlLoader.getController();
        statsController.setMain(this);

        testingStage = new Stage();
        testingStage.setTitle("Тестирование");
        testingStage.setScene(new Scene(fxmlLoader.getRoot(), 800, 600));
    }

    public void toggleTesting(String fileName) {
        if(testingStage.isShowing()) {
            testingStage.close();
        } else {
            testingStage.show();
            statsController.load(fileName);
        }
    }

    public Stage getTestingStage() {
        return testingStage;
    }

    public Controller getMainController() {
        return mainController;
    }

    public StatsController getStatsController() {
        return statsController;
    }
}
