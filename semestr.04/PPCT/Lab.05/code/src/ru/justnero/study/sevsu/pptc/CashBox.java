package ru.justnero.study.sevsu.pptc;

import java.util.HashMap;
import java.util.Map;

public class CashBox {

    private static final Map<String, CashBox> instance = new HashMap<>();

    private String name;
    private Report report;

    private CashBox(String reportName) {
        this.name = name;
        report = new Report(reportName);
    }

    public static CashBox getInstance() {
        return getInstance("Default");
    }

    public static CashBox getInstance(String name) {
        CashBox inst = instance.get(name);
        if(inst == null) {
            inst = new CashBox(name+" CashBox report");
            instance.put(name, inst);
        }
        return inst;
    }


    public Bill createBill(Invoice invoice) {
        report.addLine("Created bill ", invoice);
        return new Bill(invoice);
    }

    public CashBox pay(Bill bill, IConsumer consumer) {
        takeMoney(consumer, bill.getSum());
        bill.pay();
        bill.takeItems(consumer);
        Stock stock = Stock.getInstance(name);
        bill.items.values().forEach(item -> stock.takeItem(item.getId(), item.getQuantity()));

        report.addLine("Payed bill ", bill);
        return this;
    }

    public CashBox refund(Bill bill, IConsumer consumer) {
        giveMoney(consumer, bill.getSum());
        bill.refund();
        bill.returnItems(consumer);
        Stock stock = Stock.getInstance(name);
        bill.items.values().forEach(stock::addItem);

        report.addLine("Refunded  bill ", bill);
        return this;
    }

    private CashBox takeMoney(IConsumer consumer, double amount) {
        consumer.money(amount, false);
        return this;
    }

    private CashBox giveMoney(IConsumer consumer, double amount) {
        consumer.money(amount, true);
        return this;
    }

    public Report createReport() {
        return report;
    }

}
