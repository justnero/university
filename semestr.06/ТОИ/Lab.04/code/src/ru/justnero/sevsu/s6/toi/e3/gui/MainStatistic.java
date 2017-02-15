package ru.justnero.sevsu.s6.toi.e3.gui;

import java.util.Map;

import javax.swing.*;

import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public class MainStatistic extends JPanel {
    private JPanel rootPanel;
    private JLabel lblSum;
    private JLabel lblConstantZipf;
    private JLabel lblMaxRang;

    public MainStatistic(int sum, int maxRang, double zipfConstant) {
        super();

        lblSum.setText(String.valueOf(sum));
        lblConstantZipf.setText(String.valueOf(zipfConstant));
        lblMaxRang.setText(String.valueOf(maxRang));
        add(rootPanel);
    }

    public static MainStatistic create(Map<String, WordStatistic> allDate) {
        int sum = allDate.values().stream()
                .mapToInt(WordStatistic::getNumber)
                .sum();

        int maxRang = allDate.values().stream()
                .mapToInt(WordStatistic::getRang)
                .max()
                .getAsInt();

        double zipfConstn = (double) allDate.values().stream()
                .mapToDouble(wordStatistic -> wordStatistic.getRang() * wordStatistic.getFrequency())
                .average()
                .getAsDouble();

        return new MainStatistic(sum, maxRang, zipfConstn);
    }
}
