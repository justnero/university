package ru.justnero.study.dsmnm.lab03;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormController {

    @FXML
    private TextField nameF;
    @FXML
    private TextField phoneF;
    @FXML
    private TextField homeF;
    @FXML
    private TextField accountF;
    @FXML
    private Button sendB;

    private TData data;
    private String action;

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        clearFields();
    }

    @FXML
    public void sendAction() {
        if (action.equalsIgnoreCase("add")) {
            if (!validateForm()) {
                return;
            }
            TData target = new TData(
                    nameF.getText(),
                    Long.valueOf(phoneF.getText()),
                    homeF.getText(),
                    Float.valueOf(accountF.getText())
            );
            controller.treeAdd(target);
        } else if (action.equalsIgnoreCase("find")) {
            TData target = new TData(
                    "",
                    Long.valueOf(phoneF.getText()),
                    "",
                    0F
            );
            TreeNode<TData> node = controller.treeFind(target);
            if (node != null) {
                setAction("del");
                setData(node.getKey(node.indexOf(target)));
            }
        } else if (action.equalsIgnoreCase("del")) {
            controller.treeDel(data);
        }
    }

    public void setData(TData data) {
        this.data = data;
        nameF.setText(data.getName());
        phoneF.setText(String.valueOf(data.getPhone()));
        homeF.setText(data.getHome());
        accountF.setText(String.valueOf(data.getAccount()));
    }

    public void setFieldsMask(int mask) {
        int i = -1;
        nameF.setDisable((mask & (1 << ++i)) == 0);
        phoneF.setDisable((mask & (1 << ++i)) == 0);
        homeF.setDisable((mask & (1 << ++i)) == 0);
        accountF.setDisable((mask & (1 << ++i)) == 0);
    }

    public void setAction(String action) {
        this.data = null;
        this.action = action;
        int mask = 0;
        if (action.equalsIgnoreCase("add")) {
            sendB.setText("Добавить");
            sendB.setDefaultButton(true);
            mask = 1 + 2 + 4 + 8;
            clearFields();
        } else if (action.equalsIgnoreCase("find")) {
            sendB.setText("Найти");
            sendB.setDefaultButton(true);
            mask = 2;
            clearFields();
        } else if (action.equalsIgnoreCase("del")) {
            sendB.setText("Удалить");
            sendB.setDefaultButton(false);
            mask = 0;
        }
        setFieldsMask(mask);
    }

    public void clearFields() {
        nameF.setText("");
        phoneF.setText("");
        homeF.setText("");
        accountF.setText("");
    }

    private boolean validateForm() {
        if (nameF.getText().isEmpty() ||
                phoneF.getText().isEmpty() ||
                homeF.getText().isEmpty() ||
                accountF.getText().isEmpty()) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            long l = Long.valueOf(phoneF.getText());
            @SuppressWarnings("unused")
            float f = Float.valueOf(accountF.getText());
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }

}
