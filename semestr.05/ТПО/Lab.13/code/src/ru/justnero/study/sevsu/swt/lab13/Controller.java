package ru.justnero.study.sevsu.swt.lab13;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Controller {
    @FXML
    public TextField weightField;
    @FXML
    public Label weightLabel;
    @FXML
    public Label priceLabel;

    @FXML
    public void handleWeightChange() {
        String weightStr = weightField.getText();
        String wl = "";
        String pl = "";
        try {
            int weight = Integer.valueOf(weightStr);
            int price = 0;
            priceLabel.setTextFill(Color.BLACK);
            if (weight <= 0) {
                pl = "Вес не может быть меньше единицы";
                priceLabel.setTextFill(Color.RED);
            } else if (weight <= 20) {
                wl = "Лёгкое письмо";
                price = 25;
            } else if (weight <= 200) {
                wl = "Лёгкое письмо";
                price = 25;
                weight -= 20;
                price += (weight / 50 + ((weight % 50) > 0 ? 1 : 0)) * 35;
            } else {
                wl = "Тяжёлое письмо";
                price = 25;
                weight -= 20;
                price += (weight / 50 + ((weight % 50) > 0 ? 1 : 0)) * 35;
                weight -= 180;
                price += (weight / 25 + ((weight % 25) > 0 ? 1 : 0)) * 10;
            }
            if(price > 0) {
                pl = String.valueOf(price) + " рублей";
            }
        } catch (NumberFormatException ex) {
            pl = "Вес должен быть числом";
            priceLabel.setTextFill(Color.DARKRED);
        }
        weightLabel.setText(wl);
        priceLabel.setText(pl);
    }
}
