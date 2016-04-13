package ru.justnero.study.sevsu.pptc;

import java.util.Map;

public interface IConsumer {

    void money(double amount, boolean give);

    Map<Integer, Item> items();

    void item(Item item, boolean give);

    void items(Map<Integer, Item> items, boolean give);

}
