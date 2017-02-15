package ru.justnero.sevsu.s6.toi.e3.gui;

import java.util.Map;

import javax.swing.*;

import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public class KeyWordStatistic extends JPanel {
    private JPanel rootPanel;
    private JLabel lblSum;
    private JLabel lblNumberWords;
    private JLabel lblRangRange;

    protected int minRang;
    protected int maxRang;


    public KeyWordStatistic(int sum, int wordsNumber, int minRang, int maxRang) {
        super();
        this.minRang = minRang;
        this.maxRang = maxRang;

        lblSum.setText(String.valueOf(sum));
        lblNumberWords.setText(String.valueOf(wordsNumber));
        lblRangRange.setText("[ " + String.valueOf(minRang) + ", " + String.valueOf(maxRang) + " ]");

        add(rootPanel);
    }

    public int getMinRang() {
        return minRang;
    }

    public int getMaxRang() {
        return maxRang;
    }

    public static KeyWordStatistic create(Map<String, WordStatistic> keyWord) {
        int sum = keyWord.values().stream()
                .mapToInt(WordStatistic::getNumber)
                .sum();

        int wordsNumber = keyWord.size();

        int minRange = keyWord.values().stream()
                .mapToInt(WordStatistic::getRang)
                .min()
                .orElseGet(() -> -1);

        int maxRange = keyWord.values().stream()
                .mapToInt(WordStatistic::getRang)
                .max()
                .orElseGet(() -> -1);

        return new KeyWordStatistic(sum, wordsNumber, minRange, maxRange);
    }
}
