package ru.justnero.study.sevsu.java.lab04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Comparator;

public class CD {

    protected String title;
    protected String author;
    protected int trackCount;
    protected int duration;

    public CD(String title, String author, int trackCount, int duration) {
        setTitle(title);
        setAuthor(author);
        setTrackCount(trackCount);
        setDuration(duration);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void copyFrom(CD cd) {
        setTitle(cd.getTitle());
        setAuthor(cd.getAuthor());
        setTrackCount(cd.getTrackCount());
        setDuration(cd.getDuration());
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

    public static class Comp implements Comparator<CD> {

        protected final int field;

        public Comp(int field) {
            this.field = field;
        }

        @Override
        public int compare(CD cd1, CD cd2) {
            switch(field) {
                case 0:
                    return cd1.getTitle().compareTo(cd2.getTitle());
                case 1:
                    return cd1.getAuthor().compareTo(cd2.getAuthor());
                case 2:
                    return cd1.getTrackCount() - cd2.getTrackCount();
                case 3:
                    return cd1.getDuration() - cd2.getDuration();
            }
            return 0;
        }
    }
}
