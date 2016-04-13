package ru.justnero.study.sevsu.pptc.util;

import ru.justnero.study.sevsu.pptc.IConsumer;
import ru.justnero.study.sevsu.pptc.Item;
import ru.justnero.study.sevsu.pptc.Stock;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.util.Map;

public class FakeConsumer implements IConsumer {

    private String name;
    private double money;
    private Stock stock;

    public FakeConsumer(String name, double money) {
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
        throw new NotImplementedException();
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
