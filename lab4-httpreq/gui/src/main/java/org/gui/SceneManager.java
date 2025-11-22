package org.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.client.service.TrafficStatsService;
import org.gui.controller.MainViewController;

import java.io.IOException;

public class SceneManager {

    private final Stage primaryStage;
    private final TrafficStatsService statsService;

    public SceneManager(Stage primaryStage, TrafficStatsService statsService) {
        this.primaryStage = primaryStage;
        this.statsService = statsService;
    }

    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/gui/main-view.fxml"));
            Parent root = loader.load();

            MainViewController controller = loader.getController();
            controller.setStatsService(statsService);
            controller.initData();

            Scene scene = new Scene(root, 1100, 650);
            primaryStage.setTitle("TranStat – Liczba wejść/wyjść statków (dzienna)");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load main-view.fxml", e);
        }
    }
}
