package ru.justnero.study.dsmnm.lab03;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TimeLog {

    private SimpleStringProperty name;
    private SimpleLongProperty time;

    public TimeLog(String name, long time) {
        this.name = new SimpleStringProperty(name);
        this.time = new SimpleLongProperty(time);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public long getTime() {
        return time.get();
    }

    public SimpleLongProperty timeProperty() {
        return time;
    }

    public void setTime(long time) {
        this.time.set(time);
    }
}
