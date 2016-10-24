package ru.justnero.study.dsmnm.lab04;

public class InternalHashFunction<Data extends Comparable<Data>> implements IHashFunction<Data> {

    private final int max;

    InternalHashFunction(int limit) {
        this.max = limit - 1;
    }

    @Override
    public int max() {
        return max;
    }

    @Override
    public int get(Data data) {
        return data.hashCode() % (max + 1);
    }

}
