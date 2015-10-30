package ru.justnero.univercity.semestr03.oop.lab08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TFile {
    
    public final String name;
    public final TTime time;
    public long views;
    
    public TFile(String name,TTime time,long views) {
        this.name = name;
        this.time = time;
        this.views = views;
    }
    
    public void write(DataOutputStream out) throws IOException {
        out.writeUTF(name);
        time.write(out);
        out.writeLong(views);
    }
    
    public static TFile read(DataInputStream in) throws IOException {
        String name = in.readUTF();
        TTime time = TTime.read(in);
        long views = in.readLong();
        
        return new TFile(name,time,views);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(time.day <= 9) {
            sb.append("0");
        }
        sb.append(time.day);
        sb.append(".");
        if(time.month <= 9) {
            sb.append("0");
        }
        sb.append(time.month);
        sb.append(".");
        sb.append(time.year);
        sb.append(" ");
        if(time.hour <= 9) {
            sb.append("0");
        }
        sb.append(time.hour);
        sb.append(":");
        if(time.minute <= 9) {
            sb.append("0");
        }
        sb.append(time.minute);
        sb.append(":");
        if(time.second <= 9) {
            sb.append("0");
        }
        sb.append(time.second);
        sb.append(": ");
        sb.append(name);
        sb.append("; Views: ");
        sb.append(views);
        return sb.toString();
    }
    
}
