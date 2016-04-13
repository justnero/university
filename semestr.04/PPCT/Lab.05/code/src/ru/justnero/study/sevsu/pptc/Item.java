package ru.justnero.study.sevsu.pptc;

public class Item {

    private final int id;
    private final String name;
    private double price;
    private int quantity;

    public Item(final int id, final String name, final double price, final int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id)
                .append(" ")
                .append(name)
                .append(" price ")
                .append(price)
                .append(" x")
                .append(quantity);
        return sb.toString();
    }
}
