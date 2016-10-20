package ru.justnero.study.dsmnm.lab03;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private TableView<TimeLog> tableView;

    private ObservableList<TimeLog> timeLogs = FXCollections.observableArrayList();
    private ITree tree = new BTree();

    private StatsController statsController;
    private Stage testingStage;

    private FormController formController;
    private Stage formStage;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        initTable();

        reloadTree();

        try {
            loadStages();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void addAction() {
        showForm("add");
    }

    @FXML
    public void findAction() {
        showForm("find");
    }

    @FXML
    public void loadAction() {
        TextInputDialog dialog = new TextInputDialog("10000");
        dialog.setTitle("Загрузка данных");
        dialog.setHeaderText("Загрузка из файла input.txt");
        dialog.setContentText("Сколько объектов загрузить:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> {
            try {
                @SuppressWarnings("unused")
                int cnt = Integer.valueOf(str);
                load(cnt);
            } catch (NumberFormatException ignored) {
            }
        });
    }

    private void load(int count) {
        int i = 0;
        try (Scanner inp = new Scanner(Paths.get("input.txt"))) {
            inp.nextLine();
            long tmp, time = 0;
            for (i = 0; i < count; i++) {
                TData data = TData.read(inp);
                tmp = System.nanoTime();
                tree.add(data);
                time += System.nanoTime() - tmp;
            }
            reloadTree();
            timeLogs.add(new TimeLog("Загрузка", time));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(i);
        }
    }

    @FXML
    public void testAction() {
        if(!testingStage.isShowing()) {
            testingStage.show();
        }
        statsController.load("input.txt");
    }

    public void treeAdd(TData data) {
        long time = System.nanoTime();
        tree.add(data);
        time = System.nanoTime() - time;
        timeLogs.add(new TimeLog("Добавление", time));
        reloadTree();

        hideForm();
    }

    public TreeNode<TData> treeFind(TData data) {
        TreeNode<TData> ret;
        long time = System.nanoTime();
        ret = tree.find(data);
        time = System.nanoTime() - time;
        timeLogs.add(new TimeLog("Поиск", time));

        return ret;
    }

    public void treeDel(TData data) {
        long time = System.nanoTime();
        tree.remove(data);
        time = System.nanoTime() - time;
        timeLogs.add(new TimeLog("Удаление", time));
        reloadTree();

        hideForm();
    }

    private void showForm(String action) {
        formController.setAction(action);
        if(!formStage.isShowing()) {
            formStage.show();
        }
    }

    private void hideForm() {
        formStage.close();
    }

    private void loadStages() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("stats.fxml").openStream());
        statsController = fxmlLoader.getController();

        testingStage = new Stage();
        testingStage.setTitle("Тестирование");
        testingStage.setScene(new Scene(fxmlLoader.getRoot(), 800, 600));

        fxmlLoader = new FXMLLoader();
        fxmlLoader.load(getClass().getResource("form.fxml").openStream());
        formController = fxmlLoader.getController();
        formController.setController(this);

        formStage = new Stage();
        formStage.setTitle("Форма ввода");
        formStage.setScene(new Scene(fxmlLoader.getRoot(), 400, 200));
    }

    private void initTable() {
        TableColumn<TimeLog, String> sizeCol = new TableColumn<>("Операция");
        sizeCol.prefWidthProperty().bind(tableView.widthProperty().divide(2));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.getColumns().add(sizeCol);

        TableColumn<TimeLog, Long> timeCol = new TableColumn<>("Время");
        timeCol.prefWidthProperty().bind(tableView.widthProperty().divide(2).subtract(1));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        tableView.getColumns().add(timeCol);

        tableView.setItems(timeLogs);
    }

    private void reloadTree() {
        treeView.setRoot(tree.convert());
    }

}
