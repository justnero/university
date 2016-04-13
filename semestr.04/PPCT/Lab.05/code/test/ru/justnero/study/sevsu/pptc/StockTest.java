package ru.justnero.study.sevsu.pptc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class StockTest {

    private String name;
    private Stock stock;

    @Before
    public void setUpBeforeClass() throws Exception {
        name = new BigInteger(130, new Random()).toString(32);
        stock = Stock.getInstance(name);
        stock   .addItem(new Item(1,      "Монитор", 149.99D, 20))
                .addItem(new Item(2,   "Клавиатура",  49.99D, 55))
                .addItem(new Item(3,         "Мышь",  29.99D, 50))
                .addItem(new Item(4,       "Корпус",  99.99D, 10))
                .addItem(new Item(5,    "Процессор", 199.99D,  5))
                .addItem(new Item(6, "Жёсткий диск", 129.99D, 15));
    }

    @Test
    public void fill() throws Exception {
        assertThat(stock.getSize(), is(6));
        assertEquals(stock.getItem(5).getName(), "Процессор");
    }

    @Test
    public void modify() throws Exception {
        assertThat(stock.takeItem(4, 1).getQuantity(), is(1));
        assertNull(stock.takeItem(4, 10));
        assertThat(stock.getItem(4).getQuantity(), is(9));
    }

    @Test
    public void report() throws Exception {
        fill();
        modify();

        Report report = stock.createReport();

        report.print();
        assertThat(report.getSize(), is(10));
    }

    @After
    public void tearDownAfter() throws Exception {
        System.out.println();
    }

}
