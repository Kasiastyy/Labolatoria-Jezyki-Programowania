package org.gui.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.client.model.ShipTrafficEntry;
import org.client.service.TrafficStatsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainViewController {

    @FXML
    private ComboBox<String> portComboBox;

    @FXML
    private ComboBox<String> directionComboBox;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Button fetchButton;

    @FXML
    private TableView<ShipTrafficEntry> statsTable;

    @FXML
    private TableColumn<ShipTrafficEntry, String> colDate;

    @FXML
    private TableColumn<ShipTrafficEntry, String> colPort;

    @FXML
    private TableColumn<ShipTrafficEntry, String> colDirection;

    @FXML
    private TableColumn<ShipTrafficEntry, Number> colValue;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis chartXAxis;

    @FXML
    private NumberAxis chartYAxis;

    @FXML
    private Label statusLabel;

    private TrafficStatsService statsService;

    private final ObservableList<ShipTrafficEntry> tableData =
            FXCollections.observableArrayList();

    public void setStatsService(TrafficStatsService statsService) {
        this.statsService = statsService;
    }

    @FXML
    private void initialize() {
        colDate.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getDate() != null
                                ? cell.getValue().getDate().toString()
                                : "")
        );
        colPort.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        Objects.toString(cell.getValue().getPortName(), ""))
        );
        colDirection.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        Objects.toString(cell.getValue().getDirectionNamePl(), ""))
        );
        colValue.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getValue()));

        statsTable.setItems(tableData);

        fetchButton.setOnAction(e -> onFetchClicked());

        fromDatePicker.setValue(LocalDate.now().minusMonths(1));
        toDatePicker.setValue(LocalDate.now());
    }

    public void initData() {
        if (statsService == null) {
            throw new IllegalStateException("TrafficStatsService is not set");
        }

        List<String> ports = statsService.getAvailablePorts();
        portComboBox.setItems(FXCollections.observableArrayList(ports));

        List<String> directions = statsService.getAvailableDirectionsPl();

        // DODAJEMY "Oba"
        directions.add(0, "Oba");

        directionComboBox.setItems(FXCollections.observableArrayList(directions));

        // automatyczne pierwsze pobranie
        onFetchClicked();

        statusLabel.setText("Załadowano porty: " + ports.size()
                + ", kierunki: " + directions.size());
    }

    private void onFetchClicked() {
        if (statsService == null) {
            statusLabel.setText("Brak serwisu statystyk.");
            return;
        }

        String port = portComboBox.getValue();
        String direction = directionComboBox.getValue();
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        try {
            List<ShipTrafficEntry> data;

            if ("Oba".equals(direction)) {
                // pobieramy całość
                data = statsService.getAll();

                if (port != null) {
                    data = data.stream()
                            .filter(e -> port.equals(e.getPortName()))
                            .collect(Collectors.toList());
                }

            } else if (port == null && direction == null) {
                data = statsService.getAll();

            } else if (port != null && direction == null) {
                data = statsService.getByPort(port);

            } else if (port == null) {
                data = statsService.getByDirectionPl(direction);

            } else {
                data = statsService.getByPort(port).stream()
                        .filter(e -> direction.equals(e.getDirectionNamePl()))
                        .collect(Collectors.toList());
            }

            // filtrowanie po datach
            if (from != null) {
                data = data.stream()
                        .filter(e -> e.getDate() == null || !e.getDate().isBefore(from))
                        .collect(Collectors.toList());
            }
            if (to != null) {
                data = data.stream()
                        .filter(e -> e.getDate() == null || !e.getDate().isAfter(to))
                        .collect(Collectors.toList());
            }

            tableData.setAll(data);
            updateChart(data);

            statusLabel.setText("Załadowano " + data.size() + " rekordów.");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            statusLabel.setText("Błąd pobierania danych: " + ex.getMessage());
        }
    }

    private void updateChart(List<ShipTrafficEntry> data) {
        barChart.getData().clear();
        if (data.isEmpty()) return;

        Map<LocalDate, Map<String, Double>> grouped =
                data.stream()
                        .filter(e -> e.getDate() != null)
                        .collect(Collectors.groupingBy(
                                ShipTrafficEntry::getDate,
                                Collectors.groupingBy(
                                        e -> Objects.toString(e.getDirectionNamePl(), "brak"),
                                        Collectors.summingDouble(ShipTrafficEntry::getValue)
                                )
                        ));

        XYChart.Series<String, Number> wejscieSeries = new XYChart.Series<>();
        wejscieSeries.setName("Wejście");

        XYChart.Series<String, Number> wyjscieSeries = new XYChart.Series<>();
        wyjscieSeries.setName("Wyjście");

        List<LocalDate> sortedDates = grouped.keySet().stream()
                .sorted()
                .toList();

        for (LocalDate date : sortedDates) {
            Map<String, Double> byDir = grouped.get(date);

            double wej = byDir.getOrDefault("Wejście", 0.0);
            double wyj = byDir.getOrDefault("Wyjście", 0.0);

            String x = date.toString();

            wejscieSeries.getData().add(new XYChart.Data<>(x, wej));
            wyjscieSeries.getData().add(new XYChart.Data<>(x, wyj));
        }

        barChart.getData().addAll(wejscieSeries, wyjscieSeries);

        chartXAxis.setLabel("Data");
        chartYAxis.setLabel("Liczba wejść/wyjść");
    }
}
