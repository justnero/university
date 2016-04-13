package ru.justnero.study.sevsu.pptc;

public class Main {

    public static void main(String args[]) {
        Stock stock = Stock.getInstance();
        stock.addItem(new Item(1, "Монитор", 149.99D, 20));
        stock.addItem(new Item(2, "Клавиатура", 49.99D, 55));
        stock.addItem(new Item(3, "Мышь", 29.99D, 50));
        stock.addItem(new Item(4, "Монитор", 149.99D, 20));
        stock.addItem(new Item(5, "Монитор", 149.99D, 20));
        stock.addItem(new Item(6, "Монитор", 149.99D, 20));
    }

}
