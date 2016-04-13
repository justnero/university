package ru.justnero.study.sevsu.pptc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.justnero.study.sevsu.pptc.util.FakeConsumer;

import java.math.BigInteger;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AdminTest {

    private String      name;
    private AdminPanel  panel;

    @Before
    public void setUp() throws Exception {
        name  = new BigInteger(130, new Random()).toString(32);
        panel = AdminPanel.getInstance(name);

        Stock stock = Stock.getInstance(name);
        stock   .addItem(new Item(1,      "Монитор", 149.99D, 20))
                .addItem(new Item(2,   "Клавиатура",  49.99D, 55))
                .addItem(new Item(3,         "Мышь",  29.99D, 50))
                .addItem(new Item(4,       "Корпус",  99.99D, 10))
                .addItem(new Item(5,    "Процессор", 199.99D,  5))
                .addItem(new Item(6, "Жёсткий диск", 129.99D, 15));

        Invoice invoice = new Invoice();
        invoice .addItem(new Item(1,      "Монитор", 149.99D,  1))
                .addItem(new Item(2,   "Клавиатура",  49.99D,  1))
                .addItem(new Item(2,   "Клавиатура",  49.99D,  1));

        IConsumer consumer = new FakeConsumer(name, 1000.0D);
        CashBox cashBox = CashBox.getInstance(name);
        Bill bill = cashBox.createBill(invoice);
        cashBox.pay(bill, consumer);

        cashBox.refund(bill, consumer);
    }

    @Test
    public void order() throws Exception {
        panel   .orderItem(new Item(1,   "Монитор", 149.99D,  5))
                .orderItem(new Item(3,      "Мышь",  29.99D,  2))
                .orderItem(new Item(5, "Процессор", 199.99D,  1))
                .orderItem(new Item(5, "Процессор", 199.99D,  2));

        Stock stock = Stock.getInstance(name + " to order");
        assertThat(stock.getSize(), is(3));
        assertEquals("Мышь", stock.getItem(3).getName());
        assertThat(panel.printReports(), is(14));
    }

    @After
    public void tearDown() throws Exception {
        System.out.println();
    }
}
