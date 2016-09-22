package ru.justnero.study.dsmnm.lab01;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane grid;
    @FXML
    private TreeView tree;
    @FXML
    private ListView list;
    @FXML
    private TextField fileNameF;
    @FXML
    private TextField countF;
    @FXML
    private GridPane formGrid;
    @FXML
    private GridPane controlGrid;
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
    private TextArea log;

    private String action = "";
    private TData target;
    private BinTree binTree = new BinTree();
    private ObservableList<TData> linkedList = FXCollections.observableList(new LinkedList<>());

    @FXML
    public void initialize() {
        tree.setRoot(binTree.convert());
        list.setItems(linkedList);

        editB.setDisable(true);
        delB.setDisable(true);

        formNameF.setDisable(true);
        formPhoneF.setDisable(true);
        formHomeF.setDisable(true);
        formAccountF.setDisable(true);
        formSubmitB.setDisable(true);
        formCancelB.setDisable(true);
    }

    @FXML
    public void handleLoadAction() {
        String fileName = fileNameF.getText();
        int count = 0;
        try {
            count = Integer.valueOf(countF.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        binTree.clear();
        linkedList.clear();
        int i = -1;
        try (Scanner inp = new Scanner(Paths.get(fileName))) {
            inp.nextLine();
            long tmp, treeTime = 0, listTime = 0;
            for (i = 0; i < count; i++) {
                TData data = TData.read(inp);
                tmp = System.nanoTime();
                binTree.add(data);
                treeTime += System.nanoTime() - tmp;
                tmp = System.nanoTime();
                linkedList.add(data);
                listTime += System.nanoTime() - tmp;
            }
            log.appendText("Загрузка дерева: " + String.valueOf(treeTime) + "ns\n");
            log.appendText("Загрузка списка: " + String.valueOf(listTime) + "ns\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(i);
        }

        tree.setRoot(binTree.convert());
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

        formNameF.setDisable(false);
        formPhoneF.setDisable(true);
        formHomeF.setDisable(true);
        formAccountF.setDisable(true);
        formSubmitB.setDisable(false);
        formCancelB.setDisable(false);

        action = "find";
    }

    @FXML
    public void handleDelAction() {
        long time = System.nanoTime();
        binTree.remove(target);
        time = System.nanoTime() - time;
        log.appendText("Удаление из дерева: " + String.valueOf(time) + "ns\n");

        time = System.nanoTime();
        linkedList.remove(target);
        time = System.nanoTime() - time;
        log.appendText("Удаление из списка: " + String.valueOf(time) + "ns\n");

        tree.setRoot(binTree.convert());
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
                binTree.add(target);
                time = System.nanoTime() - time;
                log.appendText("Добавление в дерево: " + String.valueOf(time) + "ns\n");
                time = System.nanoTime();
                linkedList.add(target);
                time = System.nanoTime() - time;
                log.appendText("Добавление в список: " + String.valueOf(time) + "ns\n");
                tree.setRoot(binTree.convert());
                handleFormCancel();
                break;
            case "edit":
                if (!validateForm()) {
                    break;
                }
                time = System.nanoTime();
                binTree.remove(target);
                time = System.nanoTime() - time;
                log.appendText("Удаление из дерева: " + String.valueOf(time) + "ns\n");

                time = System.nanoTime();
                linkedList.remove(target);
                time = System.nanoTime() - time;
                log.appendText("Удаление из списка: " + String.valueOf(time) + "ns\n");

                target.setName(fileNameF.getText());
                target.setPhone(Long.valueOf(formPhoneF.getText()));
                target.setHome(formHomeF.getText());
                target.setAccount(Float.valueOf(formAccountF.getText()));

                time = System.nanoTime();
                binTree.add(target);
                time = System.nanoTime() - time;
                log.appendText("Добавление в дерево: " + String.valueOf(time) + "ns\n");
                time = System.nanoTime();
                linkedList.add(target);
                time = System.nanoTime() - time;
                log.appendText("Добавление в список: " + String.valueOf(time) + "ns\n");

                tree.setRoot(binTree.convert());
                handleFormCancel();
                break;
            case "find":
                if (formNameF.getText().isEmpty()) {
                    break;
                }
                time = System.nanoTime();
                BinTree.Node tmp = binTree.find(formNameF.getText());
                time = System.nanoTime() - time;
                log.appendText("Поиск в дереве: " + String.valueOf(time) + "ns\n");

                time = System.nanoTime();
                listFind(formNameF.getText());
                time = System.nanoTime() - time;
                log.appendText("Поиск в списке: " + String.valueOf(time) + "ns\n");

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

    private TData listFind(String name) {
        for (TData el : linkedList) {
            if (el.getName().equalsIgnoreCase(name)) {
                return el;
            }
        }
        return null;
    }

    private boolean validateForm() {
        if (formNameF.getText().isEmpty() ||
                formPhoneF.getText().isEmpty() ||
                formHomeF.getText().isEmpty() ||
                formAccountF.getText().isEmpty()) {
            return false;
        }
        try {
            Long.valueOf(formPhoneF.getText());
            Float.valueOf(formAccountF.getText());
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

}
