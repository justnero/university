package ru.justnero.sevsu.s6.toi.e3.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.justnero.sevsu.s6.toi.e3.main.ChartCreator.Type.FIST_LAW_ZIPF;
import static ru.justnero.sevsu.s6.toi.e3.main.ChartCreator.Type.SECOND_LAW_ZIPF;

public class ChartCreator {

    public enum Type {
        FIST_LAW_ZIPF, SECOND_LAW_ZIPF
    }

    private ChartCreator() {
    }

    public static ChartPanel get(String title, String xLabel, String yLabel,
                                 Map<String, WordStatistic> data, Type type) {

        JFreeChart lineChart = ChartFactory.createXYLineChart(
                title, xLabel, yLabel,
                createDataset(getMap(data, type), type),
                PlotOrientation.VERTICAL,
                false, true, false
        );

        makeSmallSpaceForChart(((XYPlot) lineChart.getPlot()));

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setSize(300, 100);
        return chartPanel;
    }

    private static void makeSmallSpaceForChart(XYPlot plot) {
        plot.getDomainAxis().setRange(
                plot.getDomainAxis().getLowerBound()
                        - plot.getDomainAxis().getUpperBound() / 100f,
                plot.getDomainAxis().getUpperBound());

        plot.getRangeAxis().setRange(
                plot.getRangeAxis().getLowerBound()
                        - plot.getRangeAxis().getUpperBound() / 100f,
                plot.getRangeAxis().getUpperBound()
        );

        plot.getRenderer().setSeriesStroke(0, new BasicStroke(4.0f));
    }

    public static Marker createMarker(double start, double end) {
        final IntervalMarker target = new IntervalMarker(start, end);

        target.setLabel("Диапозон ключевых слов");
        target.setLabelFont(new Font("SansSerif", Font.ITALIC, 11));
        target.setLabelAnchor(RectangleAnchor.LEFT);
        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
        target.setPaint(new Color(222, 222, 255, 128));

        return target;
    }

    private static <K extends Number, V extends Number>
    IntervalXYDataset createDataset(Map<K, V> data, Type type) {
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();

        final XYSeries series = new XYSeries("line");
        for (K key : data.keySet()) {
            series.add(key, data.get(key));
        }

        return new XYSeriesCollection(series);
    }

    public static Map<? extends Number, ? extends Number>
    getMap(Map<String, WordStatistic> allDate, Type type) {

        switch (type) {
            case FIST_LAW_ZIPF:
                Map<Long, Double> result1 = new LinkedHashMap<>();

                allDate.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .forEachOrdered(value -> result1.put((long) value.getRang(), value.getFrequency()));

                return result1;
            case SECOND_LAW_ZIPF:
                Map<Double, Long> result2 = new LinkedHashMap<>();

                allDate.values().stream()
                        .collect(Collectors.groupingBy(WordStatistic::getFrequency, Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.<Double, Long> comparingByValue().reversed())
                        .forEachOrdered(e -> result2.put(e.getKey(), e.getValue()));

                return result2;
            default:
                throw new IllegalArgumentException("Type has only two values: "
                        + FIST_LAW_ZIPF.toString() + " and "
                        + SECOND_LAW_ZIPF.toString() + ".");
        }
    }
}
