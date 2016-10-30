package ru.justnero.study.dsmnm.lab02;

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

    public void setName(String name) {
        this.name.set(name);
    }

    public long getTime() {
        return time.get();
    }

    public void setName(long time) {
        this.time.set(time);
    }

}
