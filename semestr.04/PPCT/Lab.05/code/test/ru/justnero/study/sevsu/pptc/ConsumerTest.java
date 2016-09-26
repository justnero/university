package ru.justnero.study.sevsu.pptc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.justnero.study.sevsu.pptc.util.FakeConsumer;

import java.math.BigInteger;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ConsumerTest {

    private FakeConsumer    consumer;
    private Invoice         invoice;
    private String          name;

    @Before
    public void setUp() throws Exception {
        name        = new BigInteger(130, new Random()).toString(32);
        consumer    = new FakeConsumer(name, 1000.0D);
        Stock stock = Stock.getInstance(name);
        stock   .addItem(new Item(1,      "Монитор", 149.99D, 20))
                .addItem(new Item(2,   "Клавиатура",  49.99D, 55))
                .addItem(new Item(3,         "Мышь",  29.99D, 50))
                .addItem(new Item(4,       "Корпус",  99.99D, 10))
                .addItem(new Item(5,    "Процессор", 199.99D,  5))
                .addItem(new Item(6, "Жёсткий диск", 129.99D, 15));
    }

    @Test
    public void fullCycle() throws Exception {
        invoice = new Invoice();
        invoice.addItem(new Item(1, "Монитор", 149.99D, 1))
                .addItem(new Item(2, "Клавиатура", 49.99D, 1))
                .addItem(new Item(2, "Клавиатура", 49.99D, 1));

        assertThat(invoice.getSum(), is(149.99D + 2.0D * (49.99D)));
        assertThat(invoice.items.size(), is(2));

        CashBox cashBox = CashBox.getInstance(name);
        Bill bill = cashBox.createBill(invoice);
        cashBox.pay(bill, consumer);

        assertThat(consumer.items().size(), is(2));
        assertThat(consumer.money(), is(750.03D));

        cashBox.refund(bill, consumer);

        assertThat(consumer.money(), is(1000.0D));

        Report report = CashBox.getInstance(name).createReport();

        report.print();
        assertThat(report.getSize(), is(3));
    }

    @After
    public void tearDown() throws Exception {
        System.out.println();
    }

}
