package ru.justnero.sevsu.s6.toi.e3.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.*;

import ru.justnero.sevsu.s6.toi.e3.ContainStringMap;
import ru.justnero.sevsu.s6.toi.e3.main.SplitScale;
import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public class InputForm extends JPanel {
    private JPanel rootPanel;
    private JButton btnloadFromFile;
    private JTextArea textArea1;
    private JButton btnGetZipfTable;

    protected JFileChooser fileChooser = new JFileChooser();
    protected String text;
    protected ContainStringMap res = new ContainStringMap();

    public static void main(String[] args) {
        create(null);
    }

    public static void create(Component parentComponent) {
        JOptionPane.showMessageDialog(parentComponent,
                new InputForm(),
                "Лабораторная работа 3",
                JOptionPane.PLAIN_MESSAGE);
    }

    public InputForm() {
        super();
        add(rootPanel);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        addListeners();
    }

    private void addListeners() {
        btnloadFromFile.addActionListener((ActionEvent e) -> {
            fileChooser.showDialog(null, "Загрузить");
            try {
                text = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))
                        .lines()
                        .reduce("", (s1, s2) -> s1 + "\n" + s2)
                        .trim();

                textArea1.setText(text);
                textArea1.setCaretPosition(0);
            } catch (FileNotFoundException e1) {
                textArea1.setText(e1.getMessage());
                e1.printStackTrace();
            }
        });

        btnGetZipfTable.addActionListener((ActionEvent event) -> {
            res.clear();
            Map<String, Long> finalMap = new LinkedHashMap<>();
            Map<String, WordStatistic> allDate = new LinkedHashMap<>();
            text = textArea1.getText();


            int res = JOptionPane.showConfirmDialog(this,
                    "Анализ по словам?",
                    "Выбор анализ",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (res == JOptionPane.YES_OPTION) {
                Arrays.stream(text.split(SplitScale.BY_WORDS.getSplitRegex()))
                        .filter(s -> s.length() > 0)
                        .filter(s -> s.length() > 3)
                        .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.<String, Long> comparingByValue().reversed())
                        .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
            } else {
                int k = Integer.parseInt(JOptionPane.showInputDialog(this, "количество символов: "));
                char[] chars = text.replaceAll("[. \n\t]", "").toCharArray();
                String[] strings = new String[chars.length / k];
                for (int i = 0; i < strings.length; i++) {
                    strings[i] = String.valueOf(chars, k * i, k);
                }

                Arrays.stream(strings)
                        .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.<String, Long> comparingByValue().reversed())
                        .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
            }


            long sum = finalMap.entrySet().stream()
                    .mapToLong(Map.Entry::getValue)
                    .sum();

            int index = 1;

            for (String key : finalMap.keySet()) {
                WordStatistic stat =
                        new WordStatistic(index, Math.toIntExact(finalMap.get(key)), (double) finalMap.get(key) / sum);
                allDate.put(key, stat);
                index++;
            }

            JOptionPane.showMessageDialog(this,
                OutputForm.create(allDate),
                    "Анализ текста на основе законов Зипфа",
                    JOptionPane.PLAIN_MESSAGE);
        });
    }
}
