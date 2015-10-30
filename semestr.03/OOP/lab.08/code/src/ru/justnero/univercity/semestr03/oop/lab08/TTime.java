package ru.justnero.univercity.semestr03.oop.lab08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TTime {
    
    public final int year;
    public final int month;
    public final int day;
    public final int hour;
    public final int minute;
    public final int second;
    
    public TTime(int year,int month,int day,int hour,int minute,int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
    
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(year);
        out.writeInt(month);
        out.writeInt(day);
        out.writeInt(hour);
        out.writeInt(minute);
        out.writeInt(second);
    }
    
    public static TTime read(DataInputStream in) throws IOException {
        int year = in.readInt(),
            month = in.readInt(),
            day = in.readInt(),
            hour = in.readInt(),
            minute = in.readInt(),
            second = in.readInt();
        
        return new TTime(year,month,day,hour,minute,second);
    }
    
}
