package ru.justnero.study.sevsu.java.lab05;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Comparator;

public class CD {

    protected final StringProperty title;
    protected final StringProperty author;
    protected final IntegerProperty trackCount;
    protected final IntegerProperty duration;

    public CD(String title, String author, int trackCount, int duration) {
        this.title      = new SimpleStringProperty(title);
        this.author     = new SimpleStringProperty(author);
        this.trackCount = new SimpleIntegerProperty(trackCount);
        this.duration   = new SimpleIntegerProperty(duration);
    }

    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public int getTrackCount() {
        return trackCount.get();
    }

    public int getDuration() {
        return duration.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public void setTrackCount(int trackCount) {
        this.trackCount.set(trackCount);
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public IntegerProperty trackCountProperty() {
        return trackCount;
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(getTitle());
        dos.writeUTF(getAuthor());
        dos.writeInt(getTrackCount());
        dos.writeInt(getDuration());
    }

    public static CD read(DataInputStream dis) throws IOException {
        return new CD(dis.readUTF(), dis.readUTF(), dis.readInt(), dis.readInt());
    }
}
