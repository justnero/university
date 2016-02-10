package ru.justnero.study.sevsu.java.lab02;

public abstract class CBuffer {

    public static int bufCount = 0;

    protected int bufID;
    protected int bufSize;

    public CBuffer(int count) {
        bufID = ++CBuffer.bufCount;
        bufSize = count;
    }

    public abstract void generate();

    public int getBufID() {
        return bufID;
    }

    public int getBufSize() {
        return bufSize;
    }

}
