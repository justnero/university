package ru.justnero.sevsu.s6.toi.e3.gui;

import org.jfree.chart.ChartPanel;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import ru.justnero.sevsu.s6.toi.e3.MapToObjects;
import ru.justnero.sevsu.s6.toi.e3.main.ChartCreator;
import ru.justnero.sevsu.s6.toi.e3.main.GetKeyWords;
import ru.justnero.sevsu.s6.toi.e3.main.WordStatistic;

public class OutputForm extends JPanel {
    private JPanel rootPanel;
    private JPanel panelForTables;
    private JPanel panelForCharts;
    private JPanel panelForStatisticInfo;

    private ChartPanel chartFirstLawZipf;
    private ChartPanel chartSecondLawZipf;
    private TableForm allData;
    private TableForm dataForSecondLawZipf;
    private TableForm keyWords;
    private MainStatistic mainStatistic;
    private KeyWordStatistic keyWordStatistic;

    public OutputForm(ChartPanel chartFirstLawZipf, ChartPanel chartSecondLawZipf,
                      TableForm allData, TableForm dataForSecondLawZipf,
                      TableForm keyWords,
                      MainStatistic mainStatistic, KeyWordStatistic keyWordStatistic) {
        super();
        this.chartFirstLawZipf = chartFirstLawZipf;
        this.chartSecondLawZipf = chartSecondLawZipf;
        this.allData = allData;
        this.dataForSecondLawZipf = dataForSecondLawZipf;
        this.keyWords = keyWords;
        this.mainStatistic = mainStatistic;
        this.keyWordStatistic = keyWordStatistic;

        addIntoPanels();
        settingSize();

        add(rootPanel);
    }

    public static OutputForm create(Map<String, WordStatistic> allDate) {
//        java.util.List<Integer> distinctNumbers =
//                allDate.values().stream()
//                        .map(WordStatistic::getNumber)
//                        .distinct()
//                        .sorted()
//                        .collect(Collectors.toList());
//
//
//        int numberDistinctFrequency = distinctNumbers.size();
//        int minValueInBound, maxValueBound;
//        minValueInBound = distinctNumbers.get(numberDistinctFrequency / 4 + 1);
//        maxValueBound = distinctNumbers.get(3 * numberDistinctFrequency / 4);
//        distinctNumbers.removeIf(integer -> integer < minValueInBound || integer > maxValueBound);
//
//        Map<String, WordStatistic> keyWords = new LinkedHashMap<>();
//        allDate.entrySet().stream()
//                .filter(stringWordStatisticEntry ->
//                        distinctNumbers.contains(stringWordStatisticEntry.getValue().getNumber()))
//                .forEachOrdered(entry -> keyWords.put(entry.getKey(), entry.getValue()));
        Map<String, WordStatistic> keyWords = GetKeyWords.get(allDate);

        return new OutputForm(

                ChartCreator.get("Первый закон Зипфа", "Ранг", "Частота",
                        allDate, ChartCreator.Type.FIST_LAW_ZIPF),

                ChartCreator.get("Второй закон Зипфа", "Частота", "Количество слов с одной частатой",
                        allDate, ChartCreator.Type.SECOND_LAW_ZIPF),

                new TableForm(new JTable(MapToObjects.toObj(allDate), new Object[]{"Слова", "Ранг", "Количество", "Частота"})),

                new TableForm(new JTable(MapToObjects.toObj(ChartCreator.getMap(allDate, ChartCreator.Type.SECOND_LAW_ZIPF)), new Object[]{"Частота", "Количество слов с одинаковой частатой"})),

                new TableForm(new JTable(MapToObjects.toObj(keyWords), new Object[]{"Ключевые слова", "Ранг", "Количество", "Частота"})),

                MainStatistic.create(allDate), KeyWordStatistic.create(keyWords)
        );
    }

    protected void addIntoPanels() {
        panelForCharts.add(chartFirstLawZipf);
        panelForCharts.add(chartSecondLawZipf);

        panelForTables.add(allData);
        panelForTables.add(dataForSecondLawZipf);
        panelForTables.add(keyWords);

        panelForStatisticInfo.add(mainStatistic);
        panelForStatisticInfo.add(keyWordStatistic);
    }

    protected void settingSize() {
        rootPanel.setSize(1300, 650);
        rootPanel.setPreferredSize(new Dimension(1300, 650));

        int height = 180;
        int width = 450;
        allData.getComponent(0).setPreferredSize(new Dimension(width, height));
        dataForSecondLawZipf.getComponent(0).setPreferredSize(new Dimension(width, height));
        keyWords.getComponent(0).setPreferredSize(new Dimension(width, height));

        chartFirstLawZipf.getChart().getXYPlot().clearDomainMarkers();
        chartFirstLawZipf.getChart().getXYPlot().addDomainMarker(
                ChartCreator.createMarker(
                        keyWordStatistic.getMinRang(),
                        keyWordStatistic.getMaxRang()));
    }

    private void createUIComponents() {
        panelForCharts = new JPanel(new GridLayout(2, 1));
        panelForTables = new JPanel(new GridLayout(3, 1));
        panelForStatisticInfo = new JPanel(new GridLayout(1, 2));
    }
}
