package ru.justnero.study.dsmnm.lab02;

import java.nio.file.Paths;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller extends BasicController {

    private final ObservableList<TimeLog> logLeftList = FXCollections.observableArrayList();
    private final ObservableList<TimeLog> logRightList = FXCollections.observableArrayList();
    @FXML
    private TreeView<TreeNode> treeLeft;
    @FXML
    private TreeView<TreeNode> treeRight;
    @FXML
    private TextField fileNameF;
    @FXML
    private TextField countF;
    @FXML
    private Button addB;
    @FXML
    private Button editB;
    @FXML
    private Button delB;
    @FXML
    private Button findB;
    @FXML
    private TextField formNameF;
    @FXML
    private TextField formPhoneF;
    @FXML
    private TextField formHomeF;
    @FXML
    private TextField formAccountF;
    @FXML
    private Button formSubmitB;
    @FXML
    private Button formCancelB;
    @FXML
    private TableView<TimeLog> logLeft;
    @FXML
    private TableView<TimeLog> logRight;
    private String action = "";
    private TData target;
    private ITree treeStructLeft = new AVLTree();
    private ITree treeStructRight = new BinTree();

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        treeLeft.setRoot(treeStructLeft.convert());
        treeRight.setRoot(treeStructRight.convert());

        initTable(logLeft);
        initTable(logRight);

        logLeft.setItems(logLeftList);
        logRight.setItems(logRightList);

        editB.setDisable(true);
        delB.setDisable(true);

        formNameF.setDisable(true);
        formPhoneF.setDisable(true);
        formHomeF.setDisable(true);
        formAccountF.setDisable(true);
        formSubmitB.setDisable(true);
        formCancelB.setDisable(true);
    }

    private void initTable(TableView<TimeLog> table) {
        TableColumn<TimeLog, String> nameCol = new TableColumn<>("Операция");
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(2));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        table.getColumns().add(nameCol);

        TableColumn<TimeLog, Long> timeCol = new TableColumn<>("Время");
        timeCol.prefWidthProperty().bind(table.widthProperty().divide(2));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.getColumns().add(timeCol);
    }

    @FXML
    public void handleTestAction() {
        main.toggleTesting(fileNameF.getText());
    }

    @FXML
    public void handleLoadAction() {
        String fileName = fileNameF.getText();
        int count;
        try {
            count = Integer.valueOf(countF.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        treeStructLeft.clear();
        treeStructRight.clear();
        int i = -1;
        try (Scanner inp = new Scanner(Paths.get(fileName))) {
            inp.nextLine();
            long tmp, leftTime = 0, rightTime = 0;
            for (i = 0; i < count; i++) {
                TData data = TData.read(inp);
                tmp = System.nanoTime();
                treeStructLeft.add(data);

                leftTime += System.nanoTime() - tmp;
                tmp = System.nanoTime();
                treeStructRight.add(data);
                rightTime += System.nanoTime() - tmp;
            }
            logLeftList.add(new TimeLog("Загрузка", leftTime));
            logRightList.add(new TimeLog("Загрузка", rightTime));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(i);
        }

        rebuildTrees();
    }

    @FXML
    public void handleSaveAction() {

    }

    @FXML
    public void handleAddAction() {
        addB.setDisable(true);
        editB.setDisable(true);
        delB.setDisable(true);
        findB.setDisable(true);

        formNameF.setDisable(false);
        formPhoneF.setDisable(false);
        formHomeF.setDisable(false);
        formAccountF.setDisable(false);
        formSubmitB.setDisable(false);
        formCancelB.setDisable(false);

        action = "add";
    }

    @FXML
    public void handleEditAction() {
        addB.setDisable(true);
        editB.setDisable(true);
        delB.setDisable(true);
        findB.setDisable(true);

        formNameF.setDisable(false);
        formPhoneF.setDisable(false);
        formHomeF.setDisable(false);
        formAccountF.setDisable(false);
        formSubmitB.setDisable(false);
        formCancelB.setDisable(false);

        action = "edit";

        formPhoneF.setText(String.valueOf(target.getPhone()));
        formHomeF.setText(target.getHome());
        formAccountF.setText(String.valueOf(target.getAccount()));
    }

    @FXML
    public void handleFindAction() {
        addB.setDisable(true);
        editB.setDisable(true);
        delB.setDisable(true);
        findB.setDisable(true);

        formNameF.setDisable(true);
        formPhoneF.setDisable(false);
        formHomeF.setDisable(true);
        formAccountF.setDisable(true);
        formSubmitB.setDisable(false);
        formCancelB.setDisable(false);

        action = "find";
    }

    @FXML
    public void handleDelAction() {
        long time = System.nanoTime();
        treeStructLeft.remove(target);
        time = System.nanoTime() - time;
        logLeftList.add(new TimeLog("Удаление", time));

        time = System.nanoTime();
        treeStructRight.remove(target);
        time = System.nanoTime() - time;
        logRightList.add(new TimeLog("Удаление", time));


        rebuildTrees();
        handleFormCancel();
    }

    @FXML
    public void handleFromSubmit() {
        long time;
        switch (action) {
            case "add":
                if (!validateForm()) {
                    break;
                }
                target = new TData(
                        formNameF.getText(),
                        Long.valueOf(formPhoneF.getText()),
                        formHomeF.getText(),
                        Float.valueOf(formAccountF.getText())
                );

                time = System.nanoTime();
                treeStructLeft.add(target);
                time = System.nanoTime() - time;
                logLeftList.add(new TimeLog("Добавление", time));


                time = System.nanoTime();
                treeStructRight.add(target);
                time = System.nanoTime() - time;
                logRightList.add(new TimeLog("Добавление", time));

                rebuildTrees();
                handleFormCancel();
                break;
            case "edit":
                if (!validateForm()) {
                    break;
                }
                time = System.nanoTime();
                treeStructLeft.remove(target);
                time = System.nanoTime() - time;
                logLeftList.add(new TimeLog("Удаление", time));

                time = System.nanoTime();
                treeStructRight.remove(target);
                time = System.nanoTime() - time;
                logRightList.add(new TimeLog("Удаление", time));

                target.setName(fileNameF.getText());
                target.setPhone(Long.valueOf(formPhoneF.getText()));
                target.setHome(formHomeF.getText());
                target.setAccount(Float.valueOf(formAccountF.getText()));

                time = System.nanoTime();
                treeStructLeft.add(target);
                time = System.nanoTime() - time;
                logLeftList.add(new TimeLog("Добавление", time));

                time = System.nanoTime();
                treeStructRight.add(target);
                time = System.nanoTime() - time;
                logRightList.add(new TimeLog("Добавление", time));

                rebuildTrees();
                handleFormCancel();
                break;
            case "find":
                if (formPhoneF.getText().isEmpty()) {
                    break;
                }
                TreeNode tmp;
                time = System.nanoTime();
                tmp = treeStructLeft.find(new TData(Long.valueOf(formPhoneF.getText())));
                time = System.nanoTime() - time;
                logLeftList.add(new TimeLog("Поиск", time));

                time = System.nanoTime();
                treeStructRight.find(new TData(Long.valueOf(formPhoneF.getText())));
                time = System.nanoTime() - time;
                logRightList.add(new TimeLog("Поиск", time));

                if (tmp == null) {
                    return;
                }

                target = tmp.getData();
                formNameF.setText(target.getName());
                formPhoneF.setText(String.valueOf(target.getPhone()));
                formHomeF.setText(target.getHome());
                formAccountF.setText(String.valueOf(target.getAccount()));

                addB.setDisable(true);
                editB.setDisable(false);
                delB.setDisable(false);
                findB.setDisable(true);

                formNameF.setDisable(true);
                formPhoneF.setDisable(true);
                formHomeF.setDisable(true);
                formAccountF.setDisable(true);
                formSubmitB.setDisable(true);
                formCancelB.setDisable(false);
                break;
        }
    }

    @FXML
    public void handleFormCancel() {
        addB.setDisable(false);
        editB.setDisable(true);
        delB.setDisable(true);
        findB.setDisable(false);

        formNameF.setDisable(true);
        formPhoneF.setDisable(true);
        formHomeF.setDisable(true);
        formAccountF.setDisable(true);
        formSubmitB.setDisable(true);
        formCancelB.setDisable(true);

        formNameF.setText("");
        formPhoneF.setText("");
        formHomeF.setText("");
        formAccountF.setText("");

        action = "";
        target = null;
    }

    private void rebuildTrees() {
        treeLeft.setRoot(treeStructLeft.convert());
        treeRight.setRoot(treeStructRight.convert());
    }

    private boolean validateForm() {
        if (formNameF.getText().isEmpty() ||
                formPhoneF.getText().isEmpty() ||
                formHomeF.getText().isEmpty() ||
                formAccountF.getText().isEmpty()) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            long l = Long.valueOf(formPhoneF.getText());
            @SuppressWarnings("unused")
            float f = Float.valueOf(formAccountF.getText());
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

}
