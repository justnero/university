package ru.justnero.study.sevsu.pptc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PseudoConsumerTest {

    private IConsumer consumer;

    @Before
    public void setUp() {
        String name  = new BigInteger(130, new Random()).toString(32);
        consumer = new PseudoConsumer(name, 15.3D);
        consumer.item(new Item(2, "Test 2", 43.21D, 4), true);
    }

    @Test
    public void constructTest() {
        assertThat(consumer.money(), is(15.3D));
        assertThat(consumer.items().size(), is(0));
    }

    @Test
    public void itemGiveTest() {
        consumer.item(new Item(1, "Test 1", 12.34D, 1), true);
        consumer.item(new Item(1, "Test 1", 12.34D, 2), true);

        assertThat(consumer.items().size(), is(2));
        assertThat(consumer.items().get(1).getQuantity(), is(3));
        assertThat(consumer.items().get(2).getQuantity(), is(4));
    }

    @Test
    public void itemTakeTest() {
        consumer.item(new Item(2, "Test 2", 43.21D, 1), false);

        assertThat(consumer.items().get(2).getQuantity(), is(3));
    }

    @Test
    public void moneyGiveTest() {
        consumer.money(12.34D, true);

        assertThat(consumer.money(), is(15.3D + 12.34D));
    }

    @Test
    public void moneyTakeTest() {
        try {
            consumer.money(43.21D, false);
        } catch (RuntimeException ex) {
            assertThat(ex.getMessage(), equalTo("Not enough money"));
        }

        consumer.money(12.34D, false);
        assertThat(consumer.money(), is(15.3D - 12.34D));

    }

}
