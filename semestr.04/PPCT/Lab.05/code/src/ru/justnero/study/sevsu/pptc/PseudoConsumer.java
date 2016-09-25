package ru.justnero.study.sevsu.pptc;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.util.Map;

public class PseudoConsumer implements IConsumer {

    private String name;
    private double money;
    private Stock stock;

    public PseudoConsumer(String name, double money) {
        this.name = name;
        this.money = money;
        stock = Stock.getInstance(name+" consumer");
    }

    public double money() {
        return money;
    }

    @Override
    public void money(double amount, boolean give) {
        if(give) {
            money += amount;
        } else {
            if(money >= amount) {
                money -= amount;
            } else {
                throw new RuntimeException("Not enough money");
            }
        }
    }

    @Override
    public Map<Integer, Item> items() {
        Map<Integer, Item> ret = null;
        try {
            Field itemsField = Stock.class.getDeclaredField("items");
            itemsField.setAccessible(true);
            ret = (Map<Integer, Item>) itemsField.get(stock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void item(Item item, boolean give) {
        if(give) {
            stock.addItem(item);
        } else {
            if(stock.takeItem(item.getId(), item.getQuantity()) == null) {
                throw new RuntimeException("No such item, or not enough");
            }
        }
    }

    @Override
    public void items(Map<Integer, Item> items, boolean give) {
        for(Item item : items.values()) {
            if(give) {
                stock.addItem(item);
            } else {
                if(stock.takeItem(item.getId(), item.getQuantity()) == null) {
                    throw new RuntimeException("No such item, or not enough");
                }
            }
        }
    }
}
