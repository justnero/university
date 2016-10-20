package ru.justnero.study.dsmnm.lab03;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StatsController {

    private final ObservableList<TestLog> dataList = FXCollections.observableArrayList();
    @FXML
    private TableView<TestLog> table;
    @FXML
    private LineChart<String, Long> chart;
    private List<TData> list;

    @SuppressWarnings("unused")
    @FXML
    public void initialize() {
        initTable(table, dataList);
    }

    void load(String fileName) {
        list = read(fileName, 10000);
        fillData();
    }

    private List<TData> read(String fileName, int maxCount) {
        List<TData> list = new ArrayList<>(maxCount);
        try (Scanner inp = new Scanner(Paths.get(fileName))) {
            inp.nextLine();
            for (int i = 0; i < maxCount; i++) {
                list.add(TData.read(inp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void fillData() {
        int tests[] = new int[]{50, 500, 1600, 6000};

        ITree tree = new BTree();

        XYChart.Series<String, Long> addSeries = new XYChart.Series<>();
        addSeries.setName("BTree Добавление");
        XYChart.Series<String, Long> findSeries = new XYChart.Series<>();
        findSeries.setName("BTree Поиск");
        XYChart.Series<String, Long> delSeries = new XYChart.Series<>();
        delSeries.setName("BTree Удаление");
        dataList.clear();

        for (int test : tests) {
            fillTest(dataList, test, tree, addSeries, findSeries, delSeries);
        }
        chart.getData().clear();
        chart.getData().add(addSeries);
        chart.getData().add(findSeries);
        chart.getData().add(delSeries);
    }

    private void fillTest(ObservableList<TestLog> list, int size, ITree tree,
                          XYChart.Series<String, Long> addSeries,
                          XYChart.Series<String, Long> findSeries,
                          XYChart.Series<String, Long> delSeries) {
        tree.clear();
        String category = String.valueOf(size);
        int ids[] = generateRandomIds(5, size);
        long times[] = new long[5];
        TData data;
        long time;
        long average = 0;
        for (int i = 0; i < size; i++) {
            data = this.list.get(i);
            time = System.nanoTime();
            tree.add(data);
            time = System.nanoTime() - time;
            for (int j = 0; j < 5; j++) {
                if (ids[j] == i) {
                    times[j] = time;
                    average += time;
                }
            }
        }
        average /= 5;
        list.add(new TestLog(size, "Добавление", times[0], times[1], times[2], times[3], times[4], average));
        addSeries.getData().add(new XYChart.Data<>(category, average));

        average = 0;
        for (int i = 0; i < 5; i++) {
            data = this.list.get(ids[i]);
            time = System.nanoTime();
            tree.find(data);
            time = System.nanoTime() - time;
            times[i] = time;
            average += time;
        }
        average /= 5;
        list.add(new TestLog(size, "Поиск", times[0], times[1], times[2], times[3], times[4], average));
        findSeries.getData().add(new XYChart.Data<>(category, average));

        average = 0;
        for (int i = 0; i < 5; i++) {
            data = this.list.get(ids[i]);
            time = System.nanoTime();
            tree.remove(data);
            time = System.nanoTime() - time;
            times[i] = time;
            average += time;
        }
        average /= 5;
        list.add(new TestLog(size, "Удаление", times[0], times[1], times[2], times[3], times[4], average));
        delSeries.getData().add(new XYChart.Data<>(category, average));
    }

    private int[] generateRandomIds(int count, int max) {
        int result[] = new int[count];

        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            result[i] = rnd.nextInt(max);
        }

        return result;
    }

    private void initTable(TableView<TestLog> table, ObservableList<TestLog> list) {
        TableColumn<TestLog, Integer> sizeCol = new TableColumn<>("Размер");
        sizeCol.prefWidthProperty().bind(table.widthProperty().divide(8));
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
        table.getColumns().add(sizeCol);

        TableColumn<TestLog, String> opCol = new TableColumn<>("Операция");
        opCol.prefWidthProperty().bind(table.widthProperty().divide(8));
        opCol.setCellValueFactory(new PropertyValueFactory<>("operation"));
        table.getColumns().add(opCol);

        TableColumn<TestLog, Long> timeCol;
        for (int i = 1; i <= 5; i++) {
            String is = String.valueOf(i);
            timeCol = new TableColumn<>(is);
            timeCol.prefWidthProperty().bind(table.widthProperty().divide(8));
            timeCol.setCellValueFactory(new PropertyValueFactory<>("time" + is));
            timeCol.setSortable(false);
            table.getColumns().add(timeCol);
        }

        timeCol = new TableColumn<>("Среднее");
        timeCol.prefWidthProperty().bind(table.widthProperty().divide(8).subtract(2));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeA"));
        timeCol.setSortable(false);
        table.getColumns().add(timeCol);

        table.setItems(list);
    }

}
