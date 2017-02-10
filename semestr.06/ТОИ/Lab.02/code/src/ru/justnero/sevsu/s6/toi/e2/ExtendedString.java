package ru.justnero.sevsu.s6.toi.e2;

public class ExtendedString {

    protected String string;
    protected int hash = 0;
    protected int i = 0, length;

    public ExtendedString(String str) {
        this(str, str.length());
    }

    public ExtendedString(String str, int len) {
        string = str;
        length = len;
    }

    public int hashCode() {
        hash = 0;
        for (int j = 0; j < length; j++) {
            hash = 2 * hash + string.charAt(j + i);
        }
        i = length - 1;

        return hash;
    }

    public int nextHash() {
        i++;
        int c = string.charAt(i - length) << (length - 1);
        hash = 2 * (hash - c) + string.charAt(i);

        return hash;
    }

    public boolean hasNextHash() {
        return string.length() - (i + 1) > length;
    }

    public int getHash() {
        return hash;
    }

    public int getStartPosition() {
        return i - length + 1;
    }

}
