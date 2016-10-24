package ru.justnero.study.dsmnm.lab04;

public class DummyHashFunction<Data extends Comparable<Data>> implements IHashFunction<Data> {

    @Override
    public int max() {
        return 99;
    }

    @Override
    public int get(Data data) {
        String str = data.toString();
        int key = 0;
        int len = str.length();
        if(len >= 3) {
            key = (str.charAt(len - 3) - 48) * 10;
        }
        key += str.charAt(len - 1) - 48;
        return key;
    }

}
