package ru.justnero.study.sevsu.pptc;

import java.util.HashMap;
import java.util.Map;

public class Stock {

    private final Map<Integer, Item> items;
    private final Report report;

    private static final Map<String, Stock> instance = new HashMap<>();

    private Stock(String reportName) {
        items  = new HashMap<>();
        report = new Report(reportName);
    }

    public static Stock getInstance() {
        return getInstance("Default");
    }

    public static Stock getInstance(String name) {
        Stock inst = instance.get(name);
        if(inst == null) {
            inst = new Stock(name+" Stock report");
            instance.put(name, inst);
        }
        return inst;
    }

    public int getSize() {
        return items.size();
    }

    public Stock addItem(final Item item) {
        Item cur = items.get(item.getId());
        if(cur == null) {
            items.put(item.getId(), item);
            report.addLine("Added new item ", item);
        } else {
            cur.setQuantity(cur.getQuantity() + item.getQuantity());
            report.addLine("Added item ", cur);
        }
        return this;
    }

    public Item getItem(int id) {
        Item item = items.get(id);
        if(item == null) {
            report.addLine("Item ", id, " not found");
        } else {
            report.addLine("Item found ", item);
        }
        return items.get(id);
    }

    public Stock removeItem(final int id) {
        Item item = items.get(id);
        if(item == null) {
            report.addLine("Item not removed ", id);
        } else {
            items.remove(id);
            report.addLine("Item removed ", item);
        }
        return this;
    }

    public Item takeItem(final int id, final int quantity) {
        Item cur = items.get(id);
        Item item = null;
        if(cur != null) {
            if(cur.getQuantity() >= quantity) {
                item = new Item(id, cur.getName(), cur.getPrice(), quantity);
                if(cur.getQuantity() == quantity) {
                    items.remove(id);
                    report.addLine("Item fully taken ", item);
                } else {
                    cur.setQuantity(cur.getQuantity() - quantity);
                    report.addLine("Item partly taken ", item, " left ",cur.getQuantity());
                }
            } else {
                report.addLine("Item can`t be taken ", cur, " need ", quantity - cur.getQuantity(), " more");
            }
        }
        return item;
    }

    public Report createReport() {
        return report;
    }

}
