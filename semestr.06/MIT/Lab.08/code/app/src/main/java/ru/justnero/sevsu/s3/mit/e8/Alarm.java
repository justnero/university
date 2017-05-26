package ru.justnero.sevsu.s3.mit.e8;

import java.util.Calendar;

public class Alarm {
    Alarm(int time, Calendar date, String message) {
        this.date = date;
        this.message = message;
        this.time = time;
    }

    public int time;
    public Calendar date;
    public String message;
}
