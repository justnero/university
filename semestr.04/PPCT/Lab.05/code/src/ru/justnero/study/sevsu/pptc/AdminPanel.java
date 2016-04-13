package ru.justnero.study.sevsu.pptc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminPanel {

    private static final Map<String, AdminPanel> instance = new HashMap<>();

    private final String name;
    private final Stock  toOrder;

    private AdminPanel(String name) {
        this.name = name;
        toOrder = Stock.getInstance(name + " to order");
    }

    public static AdminPanel getInstance() {
        return getInstance("Default");
    }

    public static AdminPanel getInstance(String name) {
        AdminPanel inst = instance.get(name);
        if(inst == null) {
            inst = new AdminPanel(name);
            instance.put(name, inst);
        }
        return inst;
    }

    public AdminPanel orderItem(Item item) {
        toOrder.addItem(item);
        System.out.println("Order: " + item);
        return this;
    }

    public int printReports() {
        int total = 0;
        for(Report report : collectReports()) {
            total += report.getSize();
            report.print();
        }

        return total;
    }

    private List<Report> collectReports() {
        List<Report> list = new ArrayList<>();
        list.add(                  toOrder.createReport());
        list.add(  Stock.getInstance(name).createReport());
        list.add(CashBox.getInstance(name).createReport());
        return list;
    }

}
