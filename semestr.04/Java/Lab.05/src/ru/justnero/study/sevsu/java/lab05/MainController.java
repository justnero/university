package ru.justnero.study.sevsu.java.lab05;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.Optional;

public class MainController {

    @FXML
    private TableView<CD> table;
    @FXML
    private TableColumn<CD, String>  tableTitleC;
    @FXML
    private TableColumn<CD, String>  tableAuthorC;
    @FXML
    private TableColumn<CD, Integer> tableTrackCountC;
    @FXML
    private TableColumn<CD, Integer> tableDurationC;

    @FXML
    private TextField titleF;
    @FXML
    private TextField authorF;
    @FXML
    private TextField trackCountF;
    @FXML
    private TextField durationF;

    @FXML
    private Button loadB;
    @FXML
    private Button saveB;
    @FXML
    private Button deleteB;
    @FXML
    private Button addB;

    @FXML
    private StackedBarChart<String, Integer> chart;

    private FileChooser fileChooser = new FileChooser();

    private Main main;


    public MainController() {

    }

    @FXML
    private void initialize() {
        fileChooser.setInitialDirectory(new File("."));
        chart.getXAxis().setLabel("Author");
        chart.getYAxis().setLabel("Duration");
        chart.setAnimated(false);

        table.setEditable(true);

        tableTitleC.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        tableAuthorC.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        tableTrackCountC.setCellValueFactory(cellData -> cellData.getValue().trackCountProperty().asObject());
        tableDurationC.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        tableTitleC.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableAuthorC.setCellValueFactory(new PropertyValueFactory<>("author"));
        tableTrackCountC.setCellValueFactory(new PropertyValueFactory<>("trackCount"));
        tableDurationC.setCellValueFactory(new PropertyValueFactory<>("duration"));

        tableTitleC.setCellFactory(TextFieldTableCell.forTableColumn());
        tableTitleC.setOnEditCommit(event -> {
            String val = event.getNewValue();
            if(val.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Title field can`t be empty", true);
                throw new RuntimeException("Title field can`t be empty");
            }
            event.getRowValue().setTitle(val);

            updateChart();
        });
        tableAuthorC.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAuthorC.setOnEditCommit(event -> {
            String val = event.getNewValue();
            if(val.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Author field can`t be empty", true);
                throw new RuntimeException("Author field can`t be empty");
            }
            event.getRowValue().setAuthor(val);

            updateChart();
        });
        tableTrackCountC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableTrackCountC.setOnEditCommit(event -> {
            int val = event.getNewValue();
            if(val < 1) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields has wrong format", "Track count should be greater then 1", true);
                throw new RuntimeException("Track count should be greater then 1");
            }
            event.getRowValue().setTrackCount(val);

            updateChart();
        });
        tableDurationC.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tableDurationC.setOnEditCommit(event -> {
            int val = event.getNewValue();
            if(val < 1) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Duration should be greater then 1", true);
                throw new RuntimeException("Duration should be greater then 1");
            }
            event.getRowValue().setDuration(val);

            updateChart();
        });
    }

    public void setMain(Main main) {
        this.main = main;

        table.setItems(main.getCDData());
        table.setEditable(true);
    }

    public void updateChart() {
        ObservableList<StackedBarChart.Series<String, Integer>> data = FXCollections.observableArrayList();
        Optional<XYChart.Series<String, Integer>> seriesOptional;
        for(CD cd : main.getCDData()) {
            seriesOptional = data.stream().filter(p -> p.getName().equals(cd.getTitle())).findFirst();
            if(seriesOptional.isPresent()) {
                seriesOptional.get().getData().add(new XYChart.Data<>(cd.getAuthor(), cd.getDuration()));
            } else {
                ObservableList<XYChart.Data<String, Integer>> list = FXCollections.observableArrayList();
                list.add(new XYChart.Data<>(cd.getAuthor(), cd.getDuration()));
                data.add(new XYChart.Series<>(cd.getTitle(), list));
            }
        }
        chart.getData().setAll(data);
    }

    @FXML
    public void handleAddAction() {
        String title = titleF.getText();
        String author = authorF.getText();
        String trackCountS = trackCountF.getText();
        String durationS = durationF.getText();
        if(title.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Title field can`t be empty", true);
            return;
        }
        if(author.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Author field can`t be empty", true);
            return;
        }
        if(trackCountS.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Track count field can`t be empty", true);
            return;
        }
        int trackCount;
        try {
            trackCount = Integer.valueOf(trackCountS);
            if(trackCount < 1) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields has wrong format", "Track count should be greater then 1", true);
                return;
            }
        } catch(NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields has wrong format", "Track count should be an integer", true);
            return;
        }
        if(durationS.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields can`t be empty", "Duration field can`t be empty", true);
            return;
        }
        int duration;
        try {
            duration = Integer.valueOf(durationS);
            if(duration < 1) {
                showAlert(Alert.AlertType.WARNING, "Form warning", "Fields has wrong format", "Duration should be greater then 1", true);
                return;
            }
        } catch(NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Form warning", "Fields has wrong format", "Duration should be an integer", true);
            return;
        }
        main.getCDData().add(new CD(title, author, trackCount, duration));

        updateChart();
    }

    @FXML
    public void handleDeleteAction() {
        CD cd = table.getSelectionModel().getSelectedItem();
        if(cd != null) {
            main.getCDData().remove(cd);

            updateChart();
        }
    }

    @FXML
    public void handleLoadAction() {
        fileChooser.setTitle("Load file");
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());
        if(file != null) {
            try(DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                int n = dis.readInt();
                ObservableList<CD> list = FXCollections.observableArrayList();
                for(int i=0;i<n;i++) {
                    list.add(CD.read(dis));
                }
                main.getCDData().setAll(list);
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "File load error", "Could not load file. It has wrong format or access denied.", true);
                return;
            }
        }

        updateChart();
    }

    @FXML
    public void handleSaveAction() {
        fileChooser.setTitle("Save file");
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());
        if(file != null) {
            try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                dos.writeInt(main.getCDData().size());
                for(CD cd : main.getCDData()) {
                    cd.write(dos);
                }
            } catch (IOException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "File save error", "Could not save file. Access denied.", true);
                return;
            }
        }

        updateChart();
    }


    private void showAlert(Alert.AlertType type, String title, String header, String text, boolean wait) {
        Alert alert = new Alert(type);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);

        if(wait) {
            alert.showAndWait();
        } else {
            alert.show();
        }
    }

}
