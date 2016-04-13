package ru.justnero.study.sevsu.pptc;

import java.util.HashMap;
import java.util.Map;

public class Invoice {

    protected final Map<Integer, Item> items;

    public Invoice() {
        items = new HashMap<>();
    }

    protected Invoice(Invoice invoice) {
        this.items = invoice.items;
    }

    public Invoice addItem(final Item item) {
        Item cur = items.get(item.getId());
        if(cur == null) {
            items.put(item.getId(), item);
        } else {
            cur.setQuantity(cur.getQuantity() + item.getQuantity());
        }
        return this;
    }

    public Invoice removeItem(final int id) {
        items.remove(id);
        return this;
    }

    public Invoice removeItem(final int id, final int quantity) {
        Item cur = items.get(id);
        if(cur != null) {
            if(cur.getQuantity() > quantity) {
                cur.setQuantity(cur.getQuantity() - quantity);
            } else {
                items.remove(id);
            }
        }
        return this;
    }

    public double getSum() {
        double sum = 0.0D;
        for(Item item : items.values()) {
            sum += item.getPrice() * item.getQuantity();
        }
        return sum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Item item : items.values()) {
            sb      .append(item.getName())
                    .append(" x")
                    .append(item.getQuantity())
                    .append(" ");
        }
        return sb.toString();
    }
}
