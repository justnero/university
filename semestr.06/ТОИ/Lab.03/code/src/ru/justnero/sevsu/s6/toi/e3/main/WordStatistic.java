package ru.justnero.sevsu.s6.toi.e3.main;

public class WordStatistic {
    protected int rang;
    protected int number;
    protected double frequency;

    public WordStatistic(int rang, int number, double frequency) {
        this.rang = rang;
        this.number = number;
        this.frequency = frequency;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
