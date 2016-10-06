package ru.justnero.study.dsmnm.lab02;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TestLog {

    private SimpleIntegerProperty size;
    private SimpleStringProperty operation;
    private SimpleLongProperty time1;
    private SimpleLongProperty time2;
    private SimpleLongProperty time3;
    private SimpleLongProperty time4;
    private SimpleLongProperty time5;
    private SimpleLongProperty timeA;

    public TestLog(int size, String operation, long time1, long time2, long time3, long time4, long time5, long timeA) {
        this.size = new SimpleIntegerProperty(size);
        this.operation = new SimpleStringProperty(operation);
        this.time1 = new SimpleLongProperty(time1);
        this.time2 = new SimpleLongProperty(time2);
        this.time3 = new SimpleLongProperty(time3);
        this.time4 = new SimpleLongProperty(time4);
        this.time5 = new SimpleLongProperty(time5);
        this.timeA = new SimpleLongProperty(timeA);
    }

    public int getSize() {
        return size.get();
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public String getOperation() {
        return operation.get();
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public long getTime1() {
        return time1.get();
    }

    public void setTime1(long time1) {
        this.time1.set(time1);
    }

    public long getTime2() {
        return time2.get();
    }

    public void setTime2(long time2) {
        this.time2.set(time2);
    }

    public long getTime3() {
        return time3.get();
    }

    public void setTime3(long time3) {
        this.time3.set(time3);
    }

    public long getTime4() {
        return time4.get();
    }

    public void setTime4(long time4) {
        this.time4.set(time4);
    }

    public long getTime5() {
        return time5.get();
    }

    public void setTime5(long time5) {
        this.time5.set(time5);
    }

    public long getTimeA() {
        return timeA.get();
    }

    public void setTimeA(long timeA) {
        this.timeA.set(timeA);
    }
}
