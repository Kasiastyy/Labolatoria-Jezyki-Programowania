package ui.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import ui.renderer.BoardRenderer;
import simulation.Simulator;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private GridPane boardGrid;

    @FXML
    private Label lblTotalFigures;

    @FXML
    private Label lblSearchers;

    @FXML
    private Label lblShooters;

    @FXML
    private Label lblPushers;

    @FXML
    private Label lblTreasures;

    @FXML
    private Label lblPromotions;

    @FXML
    private TextArea logArea;

    private Simulator simulator;
    private final BoardRenderer renderer = new BoardRenderer();

    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        simulator = new Simulator();
        simulator.setController(this);

        renderer.render(simulator.getBoard(), boardGrid);
        updateStats();

        boardGrid.widthProperty().addListener((obs, oldV, newV) ->
                renderer.render(simulator.getBoard(), boardGrid));

        boardGrid.heightProperty().addListener((obs, oldV, newV) ->
                renderer.render(simulator.getBoard(), boardGrid));

        timeline = new Timeline(
                new KeyFrame(Duration.millis(200), e -> {
                    renderer.render(simulator.getBoard(), boardGrid);
                    updateStats();
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void appendLog(String msg) {
        logArea.appendText(msg + "\n");
    }

    private void updateStats() {

        var board = simulator.getBoard();

        int total = 0;
        int searchers = 0;
        int shooters = 0;
        int pushers = 0;
        int treasures = 0;

        for (var f : simulator.getFigures()) {
            total++;
            if (f instanceof figures.Searcher) searchers++;
            else if (f instanceof figures.Shooter) shooters++;
            else if (f instanceof figures.Pusher) pushers++;
        }

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getCell(x, y).getTreasure() != null)
                    treasures++;
            }
        }

        lblTotalFigures.setText("Figury: " + total);
        lblSearchers.setText("Szperacze: " + searchers);
        lblShooters.setText("Strzelcy: " + shooters);
        lblPushers.setText("Spychacze: " + pushers);
        lblTreasures.setText("Skarby: " + treasures);
        lblPromotions.setText("Awansów: " + simulator.getPromotionsCount());
    }

    @FXML
    private void onStart() {
        simulator.startSimulation();
        appendLog("Start symulacji.");
    }

    @FXML
    private void onStop() {
        simulator.stopSimulation();
        appendLog("Stop symulacji.");
    }

    @FXML
    private void onReset() {
        simulator.resetSimulation();
        appendLog("Reset symulacji.");
    }
}
